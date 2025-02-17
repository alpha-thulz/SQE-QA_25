package za.co.wedela.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenService {

    private final String PASSWORD = "password";
    private final String SECRET_KEY;
    private final long EXPIRATION = 1000 * 60 * 30;

    public TokenService() {
        SECRET_KEY = new StandardPasswordEncoder().encode(PASSWORD);
    }

    public String extractUsername(String token) {
        return extractToken(token, Claims::getSubject);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String username, String token) {
        return !getExpirationDate(token).before(new Date()) && username.equals(extractUsername(token));
    }

    public Date getExpirationDate(String token) {
        return extractToken(token, Claims::getExpiration);
    }

    public <T> T extractToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
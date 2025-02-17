package za.co.wedela.backend;

import org.junit.jupiter.api.Test;
import za.co.wedela.backend.service.TokenService;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    public void testGenerateToken() {
        TokenService tokenService = new TokenService();
        String token = tokenService.generateToken("Test");
        System.out.println(token);
        assertEquals("Test", tokenService.extractUsername(token));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(tokenService.getExpirationDate(token)));

        assertTrue(tokenService.isTokenValid("Test", token));
        assertFalse(tokenService.isTokenValid("Test1", token));
    }
}

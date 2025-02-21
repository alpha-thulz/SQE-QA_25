package za.co.wedela.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.wedela.backend.dao.UserDao;
import za.co.wedela.backend.model.User;
import za.co.wedela.backend.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UsersDetailsService userDetailsService;

    public String registerUser(UserDao userDao) {
        User user = User.builder()
                .username(userDao.getUsername())
                .password(encoder.encode(userDao.getPassword()))
                .email(userDao.getEmail())
                .role(userDao.getRole())
                .build();

        user = userRepo.save(user);
        return tokenService.generateToken(user.getUsername());
    }

    public String loginUser(UserDao userDao) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDao.getUsername(), userDao.getPassword()));

        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return tokenService.generateToken(user.getUsername());
        }
        return "Bad credentials";
    }

    public User getUser(String id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getCurrentUser(String token) {
        return (User) userDetailsService.loadUserByUsername(tokenService.extractUsername(token));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(String id, UserDao userDao) {
        User user = getUser(id);

        if (user == null)
            throw new RuntimeException("User not found");

        user = User.builder()
                .username(userDao.getUsername())
                .password(encoder.encode(userDao.getPassword()))
                .email(userDao.getEmail())
                .role(userDao.getRole())
                .build();
        return userRepo.save(user);
    }

    public void deleteUser(String id) {
        User user = getUser(id);
        userRepo.delete(user);
    }
}

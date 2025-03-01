package za.co.wedela.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import za.co.wedela.backend.dao.UserDao;
import za.co.wedela.backend.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDao user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("access_token", userService.registerUser(user)));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDao user) {
        return ResponseEntity.ok(Map.of("access_token", userService.loginUser(user)));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserDao user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@NonNull HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(userService.getCurrentUser(token));
    }
}
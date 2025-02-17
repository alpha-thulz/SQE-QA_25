package za.co.wedela.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserDao user) {
        return ResponseEntity.status(HttpStatus.CONTINUE).body(userService.updateUser(id, user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
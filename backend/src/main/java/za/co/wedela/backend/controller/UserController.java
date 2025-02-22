package za.co.wedela.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import za.co.wedela.backend.dao.UserDao;
import za.co.wedela.backend.response.APIResponse;
import za.co.wedela.backend.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDao user) {
        return APIResponse.responseEntity(Map.of("access_token", userService.registerUser(user)), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDao user) {
        return APIResponse.responseEntity(Map.of("access_token", userService.loginUser(user)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return APIResponse.responseEntity(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return APIResponse.responseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserDao user) {
        return APIResponse.responseEntity(Map.of("access_token", userService.updateUser(id, user)), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return APIResponse.responseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@NonNull HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return APIResponse.responseEntity(userService.getCurrentUser(token), HttpStatus.OK);
    }
}
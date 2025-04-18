package com.ray.raydemo.controller;

import com.ray.raydemo.Service.UserService;
import com.ray.raydemo.dto.LoginRequest;
import com.ray.raydemo.dto.LoginResponse;
import com.ray.raydemo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = userService.authenticateUser(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new LoginResponse(null, "登錄失敗：" + e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        System.out.println("test!!");
        return ResponseEntity.ok("test");
    }
}
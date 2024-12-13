package com.example.controller;

import com.example.model.User;
import com.example.payload.AuthResponse;
import com.example.payload.LoginRequest;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        authService.registerUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        String token = authService.login(loginRequest);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<User> users = authService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}

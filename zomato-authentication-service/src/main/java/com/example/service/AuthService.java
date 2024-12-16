package com.example.service;

import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserRole;
import com.example.payload.LoginRequest;
import com.example.repository.UserRepository;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(User user){

        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username is already taken!");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email is already taken!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role = new Role();
        role.setUserRole(UserRole.ROLE_USER);
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()){

            // Log authentication details
            System.out.println("Authenticated user: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());

            // Set authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token
            String token = jwtUtil.generateToken(authentication);
            System.out.println("Generated Token: " + token);

            return token;

        } else {
            throw new UsernameNotFoundException("Invalid Username/Password");
        }

    }

    public void validateToken(String token) {
        jwtUtil.validateToken(token);
    }


}

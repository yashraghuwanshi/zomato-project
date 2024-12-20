package com.example.service;

import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserRole;
import com.example.payload.LoginRequest;
import com.example.repository.UserRepository;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(User user) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()))) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role = new Role();
        role.setUserRole(UserRole.ROLE_USER);
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {

            // Log authentication details
            log.debug("Authenticated user: {}", authentication.getName());
            log.debug("Authorities: {}", authentication.getAuthorities());

            // Set authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token
            String token = jwtUtil.generateToken(authentication);
            log.debug("Generated Token: {}", token);

            return token;

        } else {
            throw new UsernameNotFoundException("Invalid Username/Password");
        }

    }
}

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
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user){

        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username is already taken!");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email is already taken!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        System.out.println(user.getPassword());

        Role role = new Role();
        role.setUserRole(UserRole.ROLE_USER);

        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Name: " + authentication.getName());
            String token = jwtUtil.generateToken(authentication.getName());
            System.out.println(token);
            return token;
        } else {
            throw new UsernameNotFoundException("Invalid Username/Password");
        }

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


}

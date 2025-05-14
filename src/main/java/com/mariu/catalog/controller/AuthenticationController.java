package com.mariu.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mariu.catalog.dto.AuthUser;
import com.mariu.catalog.model.User;
import com.mariu.catalog.repository.UserRepository;
import com.mariu.catalog.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  UserRepository userRepository; 
  @Autowired
  PasswordEncoder encoder;
  @Autowired
  JwtUtil jwtUtils;

  @PostMapping("/signin")
  public String authenticateUser(@RequestBody AuthUser authUser) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authUser.getEmail(),
            authUser.getPassword()));

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtUtils.generateToken(userDetails.getUsername());
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@RequestBody AuthUser authUser) {
    if (userRepository.existsByEmail(authUser.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already taken!");
    }

    User newUser = new User(
        null,
        authUser.getEmail(),
        encoder.encode(authUser.getPassword()));

    userRepository.save(newUser);

    return ResponseEntity.ok("User registered successfully!");
  }
}
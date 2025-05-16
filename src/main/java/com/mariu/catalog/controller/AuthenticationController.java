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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

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
  public ResponseEntity<?> authenticateUser(@RequestBody AuthUser authUser, HttpServletResponse response) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authUser.getEmail(),
            authUser.getPassword()));

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 String jwt = jwtUtils.generateToken(userDetails.getUsername());

     
    Cookie jwtCookie = new Cookie("authToken", jwt);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(jwtUtils.expirationInSeconds());
    response.addCookie(jwtCookie);
  
      return ResponseEntity.ok().build()
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


  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(HttpServletResponse response) {
    
    Cookie jwtCookie = new Cookie("authToken", null);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(0);
    jwtCookie.setHttpOnly(true);

    response.addCookie(jwtCookie);
    return ResponseEntity.ok().build();
  }
}
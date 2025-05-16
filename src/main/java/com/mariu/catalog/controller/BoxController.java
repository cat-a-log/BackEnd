package com.mariu.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mariu.catalog.dto.BoxRequest;
import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.User;
import com.mariu.catalog.services.BoxService;
import com.mariu.catalog.services.UserService;

@RestController
@RequestMapping("/api/box")
public class BoxController {
  @Autowired
  UserService userService;

  @Autowired
  BoxService boxService;

  @PostMapping
  public ResponseEntity<Box> createBox(@RequestBody BoxRequest boxRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User createdBy = userService.findByEmail(userDetails.getUsername());

    Box savedBox = boxService.createBox(boxRequest, createdBy);

    return ResponseEntity.ok().body(savedBox);
  }
}

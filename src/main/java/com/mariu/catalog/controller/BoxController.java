package com.mariu.catalog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mariu.catalog.dto.BoxRequest;
import com.mariu.catalog.dto.Create;
import com.mariu.catalog.dto.Update;
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
   public ResponseEntity<Box> createBox(@Validated({ Create.class }) @RequestBody BoxRequest boxRequest) {
     Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Box savedBox = boxService.createBox(boxRequest, authenticatedUser.get());

    return ResponseEntity.ok().body(savedBox);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Box> getSingleEvent(@PathVariable Long id) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Box> box = boxService.findBox(id);
    if (!box.isPresent()) {
      return new ResponseEntity<Box>(HttpStatus.NOT_FOUND);
    }

    return ResponseEntity.ok(box.get());
  }
@GetMapping
  public ResponseEntity<Page<Box>> getAllEvents(/* to do: add filters */) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Pageable paging = PageRequest.of(0, 10);
    Page<Box> boxes = boxService.findBoxes(authenticatedUser.get(), paging);

    return ResponseEntity.ok(boxes);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBox(@PathVariable Long id) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    boxService.removeBox(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }


  @PatchMapping("/{id}")
 public ResponseEntity<Box> updateBox(@PathVariable Long id,
      @Validated({ Update.class }) @RequestBody BoxRequest updates) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Box> box = boxService.findBox(id);
    if (!box.isPresent()) {
      return new ResponseEntity<Box>(HttpStatus.NOT_FOUND);
    }

    Box updatedBox = boxService.updateBox(box.get(), updates);

    return ResponseEntity.ok(updatedBox);
  }

  private Optional<User> getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
   
    return Optional.of(userService.findByEmail(userDetails.getUsername()));
  }
}

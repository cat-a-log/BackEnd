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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mariu.catalog.dto.ItemRequest;
import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.Item;
import com.mariu.catalog.model.User;
import com.mariu.catalog.services.BoxService;
import com.mariu.catalog.services.ItemService;
import com.mariu.catalog.services.UserService;

@RestController
@RequestMapping("/api")
public class ItemController {
  @Autowired
  UserService userService;

  @Autowired
  BoxService boxService;

  @Autowired
  ItemService itemService;

  @PostMapping("/box/{boxId}/item")
  public ResponseEntity<Item> addItem(@PathVariable Long boxId, @RequestBody ItemRequest itemRequest) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Box> box = boxService.findBox(boxId);
    if (!box.isPresent()) {
      return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
    }

    Item savedItem = itemService.addItem(box.get(), itemRequest);

    return ResponseEntity.ok().body(savedItem);
  }


  @GetMapping("/item/{id}")
  public ResponseEntity<Item> getSingleItem(@PathVariable Long id) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Item> item = itemService.findItem(id);
    if (!item.isPresent()) {
      return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
    }

    return ResponseEntity.ok(item.get());
  }
@GetMapping("/box/{boxId}/item")
  public ResponseEntity<Page<Item>> getAllItems(@PathVariable Long boxId/*No filters yet */) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Box> box = boxService.findBox(boxId);
    if (!box.isPresent()) {
      return new ResponseEntity<Page<Item>>(HttpStatus.NOT_FOUND);
    }

    Pageable paging = PageRequest.of(0, 10);
    Page<Item> items = itemService.findItemsForBox(box.get(), paging);

    return ResponseEntity.ok(items);
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

package com.mariu.catalog.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mariu.catalog.dto.Create;
import com.mariu.catalog.dto.ItemResponse;
import com.mariu.catalog.dto.ItemRequest;
import com.mariu.catalog.dto.Update;
import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.Item;
import com.mariu.catalog.model.User;
import com.mariu.catalog.services.BoxService;
import com.mariu.catalog.services.ItemService;
import com.mariu.catalog.services.StorageService;
import com.mariu.catalog.services.UserService;
import com.mariu.catalog.storage.StorageException;
import com.mariu.catalog.storage.StorageFileNotFoundException;

@RestController
@RequestMapping("/api")
public class ItemController {
  @Autowired
  UserService userService;

  @Autowired
  BoxService boxService;

  @Autowired
  ItemService itemService;

  @Autowired
  StorageService storageService;

  @PostMapping(path = "/box/{boxId}/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ItemResponse> addItem(
      @PathVariable Long boxId,
      @Validated({ Create.class }) @RequestPart("item") ItemRequest itemRequest,
      @RequestPart(value = "image", required = false) MultipartFile image) {

    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Box> box = boxService.findBox(authenticatedUser.get(), boxId);
    if (!box.isPresent()) {
      return new ResponseEntity<ItemResponse>(HttpStatus.NOT_FOUND);
    }

    String filePath = null;
    if (image != null && !image.isEmpty()) {
      try {
        filePath = storageService.store(image, null);
      } catch (StorageException err) {
        return new ResponseEntity<ItemResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    Item savedItem = itemService.addItem(box.get(), itemRequest, filePath);

    return ResponseEntity.ok().body(new ItemResponse(savedItem));
  }

  @GetMapping("/item/{id}")
  public ResponseEntity<ItemResponse> getSingleItem(@PathVariable Long id) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Item> item = itemService.findItem(id, authenticatedUser.get());
    if (!item.isPresent()) {
      return new ResponseEntity<ItemResponse>(HttpStatus.NOT_FOUND);
    }

    return ResponseEntity.ok(new ItemResponse(item.get()));
  }

  @GetMapping("/box/{boxId}/item")
  public ResponseEntity<Page<ItemResponse>> getAllItems(@PathVariable Long boxId) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Box> box = boxService.findBox(authenticatedUser.get(), boxId);
    if (!box.isPresent()) {
      return new ResponseEntity<Page<ItemResponse>>(HttpStatus.NOT_FOUND);
    }

    Pageable paging = PageRequest.of(0, 10);
    Page<Item> items = itemService.findItemsForBox(box.get(), paging);
    Page<ItemResponse> response = items.map((Item item) -> new ItemResponse(item));

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/item/{id}")
  public ResponseEntity<?> deleteItem(@PathVariable Long id) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Item> item = itemService.findItem(id, authenticatedUser.get());
    if (!item.isPresent()) {
      return new ResponseEntity<Box>(HttpStatus.NOT_FOUND);
    }

    itemService.removeItem(id);
    storageService.delete(item.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping(path = "/item/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ItemResponse> updateItem(
      @PathVariable Long id,
      @Validated({ Update.class }) @RequestPart("item") ItemRequest updates,
      @RequestPart(value = "image", required = false) MultipartFile image) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Item> item = itemService.findItem(id, authenticatedUser.get());
    if (!item.isPresent()) {
      return new ResponseEntity<ItemResponse>(HttpStatus.NOT_FOUND);
    }

    String filePath = item.get().getFilePath();
    if (filePath == "placeholder.png") {
      filePath = null;
    }
    if (image != null && !image.isEmpty()) {
      try {
        filePath = storageService.store(image, filePath);
      } catch (StorageException err) {
        return new ResponseEntity<ItemResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    Item updatedItem = itemService.updateItem(item.get(), updates, filePath);

    return ResponseEntity.ok(new ItemResponse(updatedItem));
  }

  private Optional<User> getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return Optional.of(userService.findByEmail(userDetails.getUsername()));
  }

  @GetMapping("/item/{id}/image")
  public ResponseEntity<Resource> itemImage(@PathVariable Long id) {
    Optional<User> authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Item> item = itemService.findItem(id, authenticatedUser.get());
    if (!item.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    Resource resource = null;
    try {
      resource = storageService.loadFromItem(item.get());
    } catch (StorageFileNotFoundException err) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    String contentType = null;
    try {
      contentType = Files.probeContentType(Paths.get(item.get().getFilePath()));
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +
            resource.getFilename() + "\"")
        .body(resource);
  }
}
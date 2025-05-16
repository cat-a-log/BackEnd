package com.mariu.catalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthUser {
  @NotBlank(message = "Email is required")
  @Email(message = "Must be a valid email")
  private String email;

  @NotBlank(message = "Password is required")
  @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Only letters, numbers and certain symbols (_, ., -) are allowed")
  private String password;

  public AuthUser(String email, String password) {

    this.password = password;
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
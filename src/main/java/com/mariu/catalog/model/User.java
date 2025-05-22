package com.mariu.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String email;
  @NotBlank(message = "A password is required")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  public User(Long id, String email, String encodedPassword) {
    this.email = email;
    this.password = encodedPassword;
  }

  public User() {

  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }
}
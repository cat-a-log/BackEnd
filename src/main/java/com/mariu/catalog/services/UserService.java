package com.mariu.catalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mariu.catalog.model.User;
import com.mariu.catalog.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
package com.mariu.catalog.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mariu.catalog.dto.BoxRequest;
import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.User;
import com.mariu.catalog.repository.BoxRepository;

@Service
public class BoxService {
  @Autowired
  private BoxRepository boxRepository;

  public Box createBox(BoxRequest boxRequest, User createdBy) {
    return boxRepository.save(new Box(createdBy, boxRequest));
  }

  public Optional<Box> findBox(Long id) {
    return boxRepository.findById(id);
  }

  public Page<Box> findBoxes(User user, Pageable paging) {
    return boxRepository.findByCreatedBy(user, paging);
  }
}
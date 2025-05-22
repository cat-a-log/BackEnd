package com.mariu.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mariu.catalog.dto.BoxRequest;
import com.mariu.catalog.dto.Color;
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

  public Optional<Box> findBox(User user, Long id) {
    return boxRepository.findByIdAndCreatedBy(id, user);
  }

  public Page<Box> findBoxes(User user, Pageable paging) {
    return boxRepository.findByCreatedBy(user, paging);
  }

  public void removeBox(Long id) {
    boxRepository.deleteById(id);
  }

  public Box updateBox(Box box, BoxRequest updates) {
    if (updates.getName() != null) {
      box.setName(updates.getName());
    }

    if (updates.getDescription() != null) {
      box.setDescription(updates.getDescription());
    }

    if (updates.getLocation() != null) {
      box.setLocation(updates.getLocation());
    }

    if (updates.getColor() != null) {
      box.setColor(Color.valueOf(updates.getColor().toUpperCase()));
    }

    return boxRepository.save(box);
  }
}
package com.mariu.catalog.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mariu.catalog.dto.ItemRequest;
import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.Item;
import com.mariu.catalog.repository.ItemRepository;


@Service
public class ItemService {
  @Autowired
  private ItemRepository itemRepository;

  public Item addItem(Box box, ItemRequest itemRequest) {
    return itemRepository.save(new Item(box, itemRequest));
  }

  public Optional<Item> findItem(Long id) {
    return itemRepository.findById(id);
  }
}

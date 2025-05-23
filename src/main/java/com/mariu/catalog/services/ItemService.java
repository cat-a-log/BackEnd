package com.mariu.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mariu.catalog.dto.ItemRequest;
import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.Item;
import com.mariu.catalog.model.User;
import com.mariu.catalog.repository.ItemRepository;

@Service
public class ItemService {
  @Autowired
  private ItemRepository itemRepository;

  public Item addItem(Box box, ItemRequest itemRequest, String filePath) {
    return itemRepository.save(new Item(box, itemRequest, filePath));
  }

  public Optional<Item> findItem(Long id, User user) {
    return itemRepository.findByIdAndBox_CreatedBy(id, user);
  }

  public Page<Item> findItemsForBox(Box box, Pageable paging) {
    return itemRepository.findByBox(box, paging);
  }

  public void removeItem(Long id) {
    itemRepository.deleteById(id);
  }

  public Item updateItem(Item item, ItemRequest updates, String filePath) {
    if (updates.getName() != null) {
      item.setName(updates.getName());
    }
    if (updates.getQuantity() != null) {
      item.setQuantity(updates.getQuantity());
    }
    if (item.getFilePath() != filePath){
      item.setFilePath(filePath);
    }

    return itemRepository.save(item);
  }
}
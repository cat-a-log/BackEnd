package com.mariu.catalog.dto;

import com.mariu.catalog.model.Item;

public class ItemResponse {
  private Long id;
  private String name;

  public ItemResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public ItemResponse(Item item) {
    this.id = item.getId();
    this.name = item.getName();
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}

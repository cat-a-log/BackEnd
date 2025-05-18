package com.mariu.catalog.dto;

import com.mariu.catalog.model.Item;

public class ItemResponse {
  private Long id;
  private String name;
  private Integer quantity;

  public ItemResponse(Long id, String name, Integer quantity) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
  }

  public ItemResponse(Item item) {
    this.id = item.getId();
    this.name = item.getName();
    this.quantity = item.getQuantity();
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

   public Integer getQuantity() {
    return this.quantity;
  }
}

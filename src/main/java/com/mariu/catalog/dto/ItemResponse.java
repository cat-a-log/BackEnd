package com.mariu.catalog.dto;

import java.time.LocalDateTime;

import com.mariu.catalog.model.Item;

public class ItemResponse {
  private Long id;
  private String name;
  private Integer quantity;
  private LocalDateTime createdAt;
  private Long boxId;

  public ItemResponse(Long id, String name, Integer quantity, LocalDateTime createdAt, Long boxId) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.createdAt = createdAt;
    this.boxId = boxId;
  }

  public ItemResponse(Item item) {
    this.id = item.getId();
    this.name = item.getName();
    this.quantity = item.getQuantity();
    this.createdAt = item.getCreatedAt();
    this.boxId = item.getBox().getId();
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

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public Long getBoxId() {
    return this.boxId;
  }
}

package com.mariu.catalog.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.Item;

public class BoxWithItemsResponse {
  private Long id;
  private String name;
  private String description;
  private String location;
  private Color color;
  private List<ItemResponse> items;

  public BoxWithItemsResponse(Box box) {
    this.id = box.getId();
    this.name = box.getName();
    this.description = box.getDescription();
    this.location = box.getLocation();
    this.color = box.getColor();
    this.items = box.getItems().stream()
        .map((Item item) -> new ItemResponse(item))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public String getLocation() {
    return this.location;
  }

  public Color getColor() {
    return this.color;
  }

  public List<ItemResponse> getItems() {
    return this.items;
  }
}

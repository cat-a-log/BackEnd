package com.mariu.catalog.dto;

import com.mariu.catalog.model.Box;

public class BoxResponse {
  private Long id;
  private String name;
  private String description;
  private String location;
  private Color color;

  public BoxResponse(Long Id, String name, String description, String location, Color color) {
    this.name = name;
    this.description = description;
    this.location = location;
    this.color = color;
  }

  public BoxResponse(Box box) {
    this.id = box.getId();
    this.name = box.getName();
    this.description = box.getDescription();
    this.location = box.getLocation();
    this.color = box.getColor();
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
}

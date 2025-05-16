package com.mariu.catalog.dto;

public class BoxRequest {
  private String name;
  private String description;
  private String location;
  private Color color = Color.RED;

  public BoxRequest(String name, String description, String location, Color color) {
    this.name = name;
    this.description = description;
    this.location = location;
    this.color = color;
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

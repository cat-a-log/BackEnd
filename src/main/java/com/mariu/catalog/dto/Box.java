package com.mariu.catalog.dto;

public class Box {
  private String name;
  private String description;
  private String location;
  private Color color = Color.RED;
  private User createdBy;

  public Box(User createdBy, String name, String description, String location, Color color) {
    this.createdBy = createdBy;
    this.name = name;
    this.description = description;
    this.location = location;
    this.color = color;
  }

  public User getCreatedBy() {
    return this.createdBy;
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

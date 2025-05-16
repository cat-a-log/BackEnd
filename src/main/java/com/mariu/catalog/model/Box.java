package com.mariu.catalog.model;

import com.mariu.catalog.dto.BoxRequest;
import com.mariu.catalog.dto.Color;

import jakarta.persistence.*;

@Entity
@Table(name = "boxes")
public class Box {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description; // to do: max 100 characters

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Color color = Color.GREEN;

  @ManyToOne
  @JoinColumn(name = "created_by_user_id", nullable = false)
  private User createdBy;

  public Box(User createdBy, String name, String description, String location, Color color) {
    this.createdBy = createdBy;
    this.name = name;
    this.description = description;
    this.location = location;
    this.color = color;
  }

   public Box(User createdBy, BoxRequest boxRequest) {
    this.createdBy = createdBy;
    this.name = boxRequest.getName();
    this.description = boxRequest.getDescription();
    this.location = boxRequest.getLocation();
    this.color = boxRequest.getColor();
  }
  
  public User getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}

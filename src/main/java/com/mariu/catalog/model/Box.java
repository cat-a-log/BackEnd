package com.mariu.catalog.model;

import java.util.ArrayList;
import java.util.List;

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
  private String description;

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Color color = Color.RED;

  @ManyToOne
  @JoinColumn(name = "created_by_user_id", nullable = false)
  private User createdBy;

  @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Item> items = new ArrayList<>();

  public Box(User createdBy, String name, String description, String location, Color color) {
    this.createdBy = createdBy;
    this.name = name;
    this.description = description;
    this.location = location;
    this.color = color;
  }

  public Box() {
  }

  public Box(User createdBy, BoxRequest boxRequest) {
    this.createdBy = createdBy;
    this.name = boxRequest.getName();
    this.description = boxRequest.getDescription();
    this.location = boxRequest.getLocation();
    this.color = Color.valueOf(boxRequest.getColor().toUpperCase());
  }

  public Long getId() {
    return this.id;
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

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}

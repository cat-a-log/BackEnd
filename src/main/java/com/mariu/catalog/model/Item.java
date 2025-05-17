package com.mariu.catalog.model;

import com.mariu.catalog.dto.ItemRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    public Item(String name, Box box) {
        this.name = name;
        this.box = box;
    }

    public Item() {
    }


     public Item(Box box, ItemRequest itemRequest) {
    this.name = itemRequest.getName();
    this.box = box;
  }
  
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}
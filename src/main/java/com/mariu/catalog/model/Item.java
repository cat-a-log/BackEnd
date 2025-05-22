package com.mariu.catalog.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(nullable = true)
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;
    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer quantity = 1;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Item(String name, int quantity, Box box, String filePath) {
        this.name = name;
        this.box = box;
        this.quantity = quantity;
        this.filePath = filePath;

    }

    public Item() {
    }

    public Item(Box box, ItemRequest itemRequest, String filePath) {
        this.name = itemRequest.getName();
        this.quantity = itemRequest.getQuantity();
        this.box = box;
        this.filePath = filePath;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        if (this.filePath == null) {

            return "placeholder.png";
        }
        return this.filePath;
    }

}
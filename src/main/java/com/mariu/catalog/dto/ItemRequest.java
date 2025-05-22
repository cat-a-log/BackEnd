package com.mariu.catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ItemRequest {
  @NotBlank(message = "Name cannot be blank", groups = { Create.class })
  @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters", groups = { Create.class,
      Update.class })
  private String name;
  @NotNull(message = "Quantity cannot be blank", groups = { Create.class })
  @Min(value = 1, message = "Minimum quantity is 1", groups = { Create.class, Update.class })
  private Integer quantity;

  public ItemRequest(String name, Integer quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public String getName() {
    return this.name;
  }

  public Integer getQuantity() {
    return this.quantity;
  }
}

package com.mariu.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItemRequest {
  @NotBlank(message = "Name cannot be blank", groups = { Create.class })
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters", groups = { Create.class,
      Update.class })
  private String name;

  public ItemRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}

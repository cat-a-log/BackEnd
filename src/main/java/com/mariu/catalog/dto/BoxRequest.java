package com.mariu.catalog.dto;

import org.hibernate.sql.Update;
import com.mariu.catalog.validation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BoxRequest {
    @NotBlank(message = "Name cannot be blank", groups = { Create.class })
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters", groups = { Create.class,
      Update.class })
  private String name;
   @NotBlank(message = "Description cannot be blank", groups = { Create.class })
  @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters", groups = { Create.class,
      Update.class })
  private String description;
   @NotBlank(message = "Location cannot be blank", groups = { Create.class })
  @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters", groups = { Create.class, Update.class })
  private String location;
  @NotNull(message = "Color is mandatory", groups = { Create.class })
  @ValidEnum(enumClass = Color.class, groups = { Create.class, Update.class })
  private String color;

 public BoxRequest(String name, String description, String location, String color) {
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

   public String getColor() {
    return this.color;
  }
}

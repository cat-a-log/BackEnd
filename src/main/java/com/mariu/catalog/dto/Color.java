package com.mariu.catalog.dto;

public enum Color {
  GREEN("70D0DC"),
  BLUE("7562E0"),
  RED("FA865F");

  private final String colorCode;

  Color(String colorCode) {
    this.colorCode = colorCode;
  }

  public String colorCode() {
    return colorCode;
  }
}

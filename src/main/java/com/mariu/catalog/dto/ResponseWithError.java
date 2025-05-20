package com.mariu.catalog.dto;

public class ResponseWithError {
  private String message;
  private Boolean error;

  public ResponseWithError() {
    this.error = false;
    this.message = "";
  }

  public ResponseWithError(String message) {
    this.error = true;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public Boolean getError() {
    return error;
  }
}
package com.pawelkrml.movies.dto;

public class MessageDTO {
  private String message;

  public MessageDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

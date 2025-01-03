package com.pawelkrml.movies.dto;

import java.util.Collection;

public class JwtResponseDTO {
  private String token;
  private String type = "Bearer";
  private String username;
  private Collection<String> roles;

  public JwtResponseDTO(String token, String username, Collection<String> roles) {
    this.token = token;
    this.username = username;
    this.roles = roles;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Collection<String> getRoles() {
    return this.roles;
  }

  public void setRoles(Collection<String> roles) {
    this.roles = roles;
  }
}

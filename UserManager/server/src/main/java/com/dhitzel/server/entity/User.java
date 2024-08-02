package com.dhitzel.server.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue
  private Long userId;

  private String email;
  private String username;
  private String password;

  private Role role;

  public User() {}

  public User(String username, String password, Role role) {
    setUsername(username);
    setPassword(password);
    setRole(role);
  }

  public User(long userId, String username, String password, Role role) {
    setUserId(userId);
    setUsername(username);
    setPassword(password);
    setRole(role);
  }

  public boolean exists() {
    if (this.userId == null) {
      return false;
    }
    return true;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public Long getUserId() {
    return this.userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    String email = username + "@gmail.com";
    setEmail(email);
    this.username = username;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return this.role;
  }
  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;
    User user = (User) o;
    return Objects.equals(this.userId, user.userId) && Objects.equals(this.username, user.username)
        && Objects.equals(this.role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.userId, this.username, this.role);
  }

  @Override
  public String toString() {
    return "User{" + "userId=" + this.userId + ", username='" + this.username + '\'' + ", role='" + this.role + '\'' + '}';
  }
}
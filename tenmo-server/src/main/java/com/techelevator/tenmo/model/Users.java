package com.techelevator.tenmo.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Users {

   private Long id;
   private String username;
   private String password;
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();

   public Users() { }

   public Users(Long id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.activated = true;
   }

   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }
   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }
   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         this.authorities.add(new Authority("ROLE_" + role));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Users users = (Users) o;
      return id == users.id &&
              activated == users.activated &&
              Objects.equals(username, users.username) &&
              Objects.equals(password, users.password) &&
              Objects.equals(authorities, users.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
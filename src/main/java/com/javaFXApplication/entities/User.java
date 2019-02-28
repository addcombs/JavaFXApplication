package com.javaFXApplication.entities;

import javax.persistence.*;

/**
 * Represents a User's Signin
 *
 * @author Adam Combs
 */
@Entity
@Table(name = "acs_users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  private String salt;

          public User() {}

          public User(String username, String password, String salt) {
                    this.username = username;
                    this.password = password;
                    this.salt = salt;
          }

          public Integer getId() {
                    return id;
          }

          public void setId(Integer id) {
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

          public String getSalt() {
                    return salt;
          }

          public void setSalt(String salt) {
                    this.salt = salt;
          }

}
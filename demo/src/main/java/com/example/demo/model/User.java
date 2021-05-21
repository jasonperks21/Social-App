package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   private String email;

   public User() {

   }

   public User(String firstname, String lastname, String email) {
      super();
      this.firstName = firstname;
      this.lastName = lastname;
      this.email = email;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getFirstName (String firstname) {
      return firstname;
   }

   public void setFirstName(String firstname) {
      this.firstName = firstname;
   }

   public String getLastName (String lastname) {
      return lastname;
   }

   public void setLastName(String lastname) {
      this.lastName = lastname;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
}

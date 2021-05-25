package com.cs334.project3.model;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long uid;

   @Column(name = "uname")
   private String uname;

   @Column(name = "dispname")
   private String dispname;

   @Column(name = "pwdhash")
   private String pwdhash;

   @Column(name = "email")
   private String email;

   public User() {

   }

   public User(String uname, String dispname, String pwdhash, String email) {
      super();
      this.uname = uname;
      this.dispname = dispname;
      //TODO Hash password before saving
      this.pwdhash = pwdhash;
      this.email = email;
   }

   public long getId() {
      return this.uid;
   }

   public String getUname () {
      return this.uname;
   }

   public void setUname(String uname) {
      this.uname = uname;
   }

   public String getDispname () {
      return this.dispname;
   }

   public void setDispname (String dispname) {
      this.dispname = dispname;
   }
   
   public String getPwd () {
      //TODO unhash password and return
      // At the moment the pwd is stored unhashed and returned as is
      return this.pwdhash;
   }

   public void setPwd (String pwdhash) {
      //TODO hash password before storing
      // At the moment the pwd is stored unhashed and returned as is
      this.pwdhash = pwdhash;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
}

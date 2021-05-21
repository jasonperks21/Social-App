package com.cs334.project3.model;

import javax.persistence.*;

@Entity
@Table(name="test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String text;
}

package com.cs334.project3.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="test2")
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String text;

    public Test(String text) {
        this.text = text;
    }
}


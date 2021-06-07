package com.cs334.project3.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsernamePassword {
    private String username;
    private Integer password;
}

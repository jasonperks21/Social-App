package com.cs334.project3.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestBody {
    private String username, displayname, email, password, avatar;
}

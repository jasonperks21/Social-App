package com.cs334.project3.service;

import com.cs334.project3.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserStatus {
    private boolean usernameError, emailError, passwordError;
    private UserDTO user;
}

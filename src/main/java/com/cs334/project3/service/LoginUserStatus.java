package com.cs334.project3.service;

import com.cs334.project3.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserStatus {
    private boolean usernameError, passwordError;
    private UserDTO user;
}
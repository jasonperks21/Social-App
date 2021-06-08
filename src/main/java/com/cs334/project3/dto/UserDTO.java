package com.cs334.project3.dto;

import com.cs334.project3.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String displayName;
    private String userName;
    private String email;
    private String avatar;

    public UserDTO(User u){
        userId=u.getUser_id();
        displayName=u.getDisplayName();
        userName=u.getUsername();
        email=u.getEmail();
        avatar = u.getAvatar_path();
    }
}

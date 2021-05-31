package com.cs334.project3.dto;

import com.cs334.project3.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO{
    private Long id;
    private String displayName;
    private boolean admin;

    public UserDTO(User user, boolean admin){
        id = user.getUser_id();
        displayName = user.getDisplayName();
        this.admin = admin;
    }


}

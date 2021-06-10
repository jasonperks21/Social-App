package com.cs334.project3.dto;

import com.cs334.project3.model.User;
import com.cs334.project3.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTOForPost {
    private Long id;
    private String displayName, avatar;
    private boolean admin;

    public UserDTOForPost(User user, boolean admin){
        id = user.getUser_id();
        displayName = user.getDisplayName();
        this.admin = admin;
        avatar = user.getAvatar_path();
    }
}

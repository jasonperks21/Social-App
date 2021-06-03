package com.cs334.project3.dto;

import com.cs334.project3.model.Friend;
import com.cs334.project3.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendDTO {
    private Long userId, friendId, friendshipId;

    public FriendDTO(Friend friend) {
        this.userId = friend.getUser().getUser_id();
        this.friendId = friend.getFriend().getUser_id();
        this.friendshipId = friend.getFriendship_id();
    }

}

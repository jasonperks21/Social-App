package com.cs334.project3.requestbody;

import lombok.Data;

@Data
public class FriendRequestBody {
    private Long userId, friendId;
}

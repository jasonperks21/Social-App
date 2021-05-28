package com.cs334.project3.repo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PostResultSetMapping {
    //post stuff
    private Long postId;
    private Long replyId;
    private String message;
    private ZonedDateTime timeStamp;

    //user stuff
    private String userDisplayName;
    private Long userId;
    private Long groupMemberId;

    //category stuff
    private String category;

    public PostResultSetMapping(Long postId, Long replyId, String message, ZonedDateTime timeStamp, String userDisplayName, Long userId, Long groupMemberId, String category) {
        this.postId = postId;
        this.replyId = replyId;
        this.message = message;
        this.timeStamp = timeStamp;
        this.userDisplayName = userDisplayName;
        this.userId = userId;
        this.groupMemberId = groupMemberId;
        this.category = category;
    }
}

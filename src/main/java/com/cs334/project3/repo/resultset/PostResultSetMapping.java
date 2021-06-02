package com.cs334.project3.repo.resultset;

import com.cs334.project3.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class PostResultSetMapping {
    //post stuff
    private String groupName;
    private ZonedDateTime timePosted;
    private Long groupId;
    private Long postId;
    private Long replyId;
    private String message;

    //user stuff
    private String userDisplayName;
    private Long userId;
    private Long groupMemberId;

    //category stuff
    private String category;
    private Long categoryId;
}

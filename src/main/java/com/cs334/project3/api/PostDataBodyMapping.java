package com.cs334.project3.api;

import lombok.Data;

@Data
public class PostDataBodyMapping {
    private Long userId, groupId, replyId, categoryId;
    private String message;

}

package com.cs334.project3.requestbody;

import lombok.Data;

@Data
public class PostRequestBodyMapping {
    private Long userId, groupId, replyId, categoryId;
    private String message;

}

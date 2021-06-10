package com.cs334.project3.requestbody;

import lombok.Data;

@Data
public class PostRequestBody {
    private Long userId, groupId, replyId, categoryId;
    private String message;
    private Double longitude, latitude;
}

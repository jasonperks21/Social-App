package com.cs334.project3.requestbody;

import lombok.Data;

@Data
public class GroupRequestBody {
    private Long userId, groupId;
    private String groupName;
    private boolean admin = false;
}

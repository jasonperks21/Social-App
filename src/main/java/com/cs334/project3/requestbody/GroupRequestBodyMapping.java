package com.cs334.project3.requestbody;

import lombok.Data;

@Data
public class GroupRequestBodyMapping {
    private Long userId, groupId;
    private String groupName;
}

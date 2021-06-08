package com.cs334.project3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupSearchDTO {
    private Long groupId;
    private String groupName;
}

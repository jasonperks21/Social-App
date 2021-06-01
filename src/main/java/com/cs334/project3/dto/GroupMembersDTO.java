package com.cs334.project3.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.cs334.project3.model.GroupMember;

@Data
@AllArgsConstructor
public class GroupMembersDTO{
    Long userId;
    Long groupId;
    boolean admin;

    public GroupMembersDTO(GroupMember groupMember){
        this.userId = groupMember.getUser().getUser_id();
        this.groupId = groupMember.getGroup().getGroup_id();
        this.admin = groupMember.getAdmin();
    }
}

package com.cs334.project3.dto;

import com.cs334.project3.model.Group;
import com.cs334.project3.model.GroupMember;
import com.cs334.project3.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupDTO {
    private Long groupId;
    private String groupName;
    private boolean isAdminOnThisGroup = false;
    private List<UserDTO> users = new ArrayList<>();

    public GroupDTO(Group group, Long userId){
        this.groupId = group.getGroup_id();
        this.groupName = group.getGroupName();
        List<GroupMember> groupMemberList = group.getMembers();
        for(GroupMember gm : groupMemberList) {
            UserDTO udto = new UserDTO(gm.getUser(), gm.getAdmin());
            users.add(udto);
            if(userId == gm.getMember_id() && gm.getAdmin()){
                isAdminOnThisGroup = true;
            }
        }
    }
}

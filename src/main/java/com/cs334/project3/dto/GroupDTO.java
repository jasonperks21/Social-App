package com.cs334.project3.dto;

import com.cs334.project3.model.Group;
import com.cs334.project3.model.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupDTO{
    private Long groupId;
    private String groupName;
    private boolean isAdminOnThisGroup = false;
    private List<UserDTOForPost> users = new ArrayList<>();

    public GroupDTO(Group group, Long userId){
        this.groupId = group.getGroup_id();
        this.groupName = group.getGroupName();
        List<GroupMember> groupMemberList = group.getMembers();

        if (groupMemberList.size() == 1) {
            for (GroupMember gm : groupMemberList) {
                UserDTOForPost udto = new UserDTOForPost(gm.getUser(), gm.getAdmin());
                users.add(udto);
            }
            isAdminOnThisGroup = true;
        } else {
            for (GroupMember gm : groupMemberList) {
                UserDTOForPost udto = new UserDTOForPost(gm.getUser(), gm.getAdmin());
                users.add(udto);
                if (userId == gm.getMember_id() && gm.getAdmin()) {
                    isAdminOnThisGroup = true;
                }
            }
        }
    }


}

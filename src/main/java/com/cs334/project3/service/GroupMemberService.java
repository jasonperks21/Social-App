package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.repo.GroupMemberRepository;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.model.GroupMember;
import com.cs334.project3.model.Group;
import com.cs334.project3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class GroupMemberService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    // Get all group members of group
    public GroupMembersDTO getAllGroupMembersByGroupId(Long groupId){
        GroupMembersDTO dto = new GroupMembersDTO();
        //TODO: DB : Implement findAllGroupMembersByGroupId in groupMemberRepository that returns List<User>
        List<User> userList = groupMemberRepository.findAllGroupMembersByGroupId(groupId);
        List<User> adminList = groupMemberRepository.findAllAdminsByGroupId(groupId);
        try {
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User u : userList){
                boolean admin = false;
                if(adminList.contains(u)){
                    admin = true;
                }
                userDTOList.add(new UserDTO(u, admin));
            }
            dto.setData(userDTOList);
            dto.ok();
        } catch(Exception e){
            dto.error();
        }
        return dto;
    }

    // Get group admin
    public GroupMembersDTO getGroupAdminsByGroupId(Long groupId){
        GroupMembersDTO dto = new GroupMembersDTO();
        //TODO: DB : Implement findAllAdminsByGroupId in groupMemberRepository that returns List<User>
        List<User> adminList = groupMemberRepository.findAllAdminsByGroupId(groupId);
        try{
            List<UserDTO> adminDTOList = new ArrayList<>();
            for (User a : adminList){
                adminDTOList.add(new UserDTO(a, true));
            }
            dto.setData(adminDTOList);
        }
        return dto;
    }

    // See if user is group admin

    // Set group admin

    // Get group member count

    // Get groups that user is a member of
    public GroupsThatUserIsMemberOfDTO getGroupsWhereUserIsMember(Long userId) {
        //get from repo
        GroupsThatUserIsMemberOfDTO dto = new GroupsThatUserIsMemberOfDTO();
        List<Group> groupList = groupRepository.getALlGroupsThatUserIsMemberOf(userId);
        try {
            //format data
            List<GroupDTO> groupDTOList = new ArrayList<>();
            for (Group g : groupList) {
                groupDTOList.add(new GroupDTO(g, userId));
            }
            dto.setData(groupDTOList);
            dto.ok();
        } catch (Exception e){
            dto.error();
        }

        return dto;
    }

}

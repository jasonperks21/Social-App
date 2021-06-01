package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.model.Post;
import com.cs334.project3.repo.GroupMemberRepository;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public void save(GroupMember groupMember) {
        groupMemberRepository.save(groupMember);
    }

    /*
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
        } catch(Exception e){
            dto.error();
        }
        return dto;
    }
    /*
    COMMENTING THESE OUT SO I CAN FIX THEM ONE AT A TIME
    // See if user is group admin
    public boolean userIdIsGroupAdmin(Long userId, Long groupId){
        User user = userRepository.findById(userId).get();
        List <User> adminList = groupMemberRepository.findAllAdminsByGroupId(groupId);
        if (adminList.contains(user)){
            return true;
        } else {
            return false;
        }
    }

    // Set group admin
    public void updateGroupAdminByUserId(Long userId, Long groupId, boolean admin){
        //TODO: DB : Implement updateGroupMembersAdmin in groupMemberRepository; sets/removes an admin
        groupMemberRepository.updateGroupMembersAdmin(userId, groupId, admin);
    }

    // Get group member count
    public Long getAmountOfGroupMembers(Long groupId){
        //TODO: DB : Implement groupMemberCount in groupMemberRepository; counts amount of members in group
        return groupMemberRepository.groupMemberCount(groupId);
    }
    */
    // Get groups that user is a member of
    //TODO: MARCO CLEAN BASEDTO SHIT
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

    public GroupMember getGroupMembership(Long userId, Long groupId){
        return groupMemberRepository.getUserGroupMembership(userId,groupId);
    }

}

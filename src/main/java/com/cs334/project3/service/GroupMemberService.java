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

    public void saveMember(GroupMember groupMember) {
        groupMemberRepository.save(groupMember);
    }

    public List<GroupMembersDTO> getGroupMembersByGroupId(Long gid){
        Group group = groupRepository.getById(gid);
        List<GroupMember> gmList = group.getMembers();
        List<GroupMembersDTO> gmDTOList;
        if(gmList == null){
            throw new NullPointerException();
        } else {
            gmDTOList = new ArrayList<>();
            for(GroupMember gm:gmList){
                gmDTOList.add(new GroupMembersDTO(gm));
            }
            return gmDTOList;
        }
    }

    // Update group admin
    public GroupMembersDTO updateGroupAdminByUserId(Long userId, Long groupId, boolean admin){
        GroupMember gm = groupMemberRepository.getUserGroupMembership(userId, groupId);
        if(gm==null){
            //TODO: Dom: Write more creative exceptions
            throw new NullPointerException();
        } else{
            gm.setAdmin(admin);
            groupMemberRepository.save(gm);
            return new GroupMembersDTO(gm);
        }
    }

    // Delete groupmember
    public GroupMembersDTO deleteGroupMemberById(Long userId, Long groupId){
        GroupMember gm = groupMemberRepository.getUserGroupMembership(userId, groupId);
        if(gm==null){
            throw new NullPointerException();
        } else{
            groupMemberRepository.deleteById(gm.getMember_id());
            return new GroupMembersDTO(gm);
        }
    }

    // Get groups that user is a member of

    public List<GroupDTO> getGroupsWhereUserIsMember(Long userId) {
        //get from repo
        GroupsThatUserIsMemberOfDTO dto = new GroupsThatUserIsMemberOfDTO();
        List<Group> groupList = groupRepository.getALlGroupsThatUserIsMemberOf(userId);
        List<GroupDTO> groupDTOList = new ArrayList<>();
        for (Group g : groupList) {
            groupDTOList.add(new GroupDTO(g, userId));
        }
        return groupDTOList;
    }

    public GroupMember getGroupMembership(Long userId, Long groupId){
        return groupMemberRepository.getUserGroupMembership(userId,groupId);
    }

}

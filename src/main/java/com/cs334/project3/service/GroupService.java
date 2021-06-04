package com.cs334.project3.service;

import com.cs334.project3.dto.GroupDTO;
import com.cs334.project3.dto.GroupMembersDTO;
import com.cs334.project3.exceptions.MethodNotAllowedException;
import com.cs334.project3.model.Group;
import com.cs334.project3.model.GroupMember;
import com.cs334.project3.model.User;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.GroupMemberRepository;
import com.cs334.project3.repo.UserRepository;
import com.cs334.project3.requestbody.GroupRequestBodyMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    /**
     * Create a new group.
     * @param group
     */
    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    public GroupDTO createGroup(GroupRequestBodyMapping grbm){
        Group group;
        GroupMember gm;
        try {
            if (userRepository.existsById(grbm.getUserId())) {
                User user = userRepository.getById(grbm.getUserId());
                group = new Group(grbm.getGroupName());
                gm = new GroupMember(group, user, true);
                groupRepository.save(group);
                groupMemberRepository.save(gm);
            } else {
                throw new NullPointerException();
            }
        } catch(Exception e){
            throw new DataAccessResourceFailureException("Something went wrong on our side");
        }
        return new GroupDTO(group, grbm.getUserId());
    }

    /**
     * Delete a group.
     * @param ids GroupRequestBodyMapping object
     */
    public GroupDTO deleteGroup(GroupRequestBodyMapping ids) {
        GroupMember gm = groupMemberRepository.getUserGroupMembership(ids.getUserId(),ids.getGroupId());
        try{
            if (gm.getAdmin()){
                Group g = gm.getGroup();
                groupRepository.delete(g);
                return new GroupDTO(g, ids.getUserId());
            } else{
                throw new MethodNotAllowedException();
            }
        } catch(Exception e){
            throw new NullPointerException();
        }
    }

    /**
     * Delete a group by ID.
     * @param group_id
     */
    public void deleteGroupById(Long group_id) {
        groupRepository.deleteById(group_id);
    }

    /**
     * See if a group exists.
     * @param group_id
     * @return
     */
    public boolean groupIdExists(Long group_id) {
        return groupRepository.existsById(group_id);
    }

    /**
     * Find a group by its ID:
     * @param group_id
     * @param user_id
     * @return
     */

    public GroupDTO getGroupById(Long group_id, Long user_id) {
        Group group = groupRepository.findById(group_id).get();
        GroupDTO groupDTO = new GroupDTO(group, user_id);
        return groupDTO;
    }
    /*
    /**
     * Find group by the groups name.
     * @param groupName
     * @return
     */
    /*
    public GroupDTO getGroupByName(String groupName, Long user_id) {
        //TODO: DB: Implement findGroupByName in GroupRepository that returns type Optional<Group>
        Group group = groupRepository.findGroupByName(groupName).get();
        GroupDTO groupDTO = new GroupDTO(group, user_id);
        return groupDTO;
    }
    */

    //TODO: Fix.
    /**
     * Add a member to the group
     * @param group_id
     * @param user_id
     */

    public GroupMembersDTO joinGroup(Long group_id, Long user_id, boolean admin) {
        User user = userRepository.getById(user_id);
        Group group;
        GroupMember gm;
        if(user==null){
            throw new NullPointerException();
        } else {
            group = groupRepository.getById(group_id);
            gm = user.addGroupMembership(group, admin);
            groupRepository.save(group);
            groupMemberRepository.save(gm);
            return new GroupMembersDTO(gm);
        }
    }
}

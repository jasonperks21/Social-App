package com.cs334.project3.service;

import com.cs334.project3.dto.GroupDTO;
import com.cs334.project3.model.Group;
import com.cs334.project3.model.GroupMember;
import com.cs334.project3.model.User;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new group.
     * @param group
     */
    public void createGroup(Group group) {
        groupRepository.save(group);
    }

    /**
     * Delete a group.
     * @param group
     */
    public void deleteGroup(Group group) {
        groupRepository.delete(group);
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

    /**
     * Find group by the groups name.
     * @param groupName
     * @return
     */
    public GroupDTO getGroupByName(String groupName, Long user_id) {
        //TODO: DB: Implement findGroupByName in GroupRepository that returns type Optional<Group>
        Group group = groupRepository.findGroupByName(groupName).get();
        GroupDTO groupDTO = new GroupDTO(group, user_id);
        return groupDTO;
    }

    /**
     * Add a member to the group
     * @param group_id
     * @param user_id
     */
    public void joinGroup(Long group_id, Long user_id) {
        //TODO: DB: Implement addNewMember in GroupRepository
        groupRepository.addNewMember(group_id, user_id);
    }
}

package com.cs334.project3.service;

import com.cs334.project3.model.Group;
import com.cs334.project3.repo.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    // Create a new group:
    public void createGroup(Group group) {
        groupRepository.save(group);
    }

    //Delete a group:
    public void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

    //See if group exists:
    public boolean groupIdExists(Long group_id) {
        return groupRepository.existsById(group_id);
    }

    //Find group by ID:
    public Group getGroupById(Long group_id) {
        return groupRepository.findById(group_id).get();
    }

    //Get number of groups that exist:
    public Long numberOfGroups() {
        return groupRepository.count();
    }

    //Find group by the groups name:
//    public Group getGroupByName(String groupName) {
//        return groupRepository.findGroupByName(groupName).get();
//    }
}

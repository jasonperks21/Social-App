package com.cs334.project3.service;

import com.cs334.project3.dto.PostsToDisplayForUserDTO;
import com.cs334.project3.model.Group;
import com.cs334.project3.model.GroupMember;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.PostRepository;
import com.cs334.project3.repo.PostResultSetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PostRepository postRepository;

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
    public Group getGroupByName(String groupName) {
        //TODO: DB: Implement findGroupByName in GroupRepository that returns type Optional<Group>
        return groupRepository.findGroupByName(groupName).get();
    }

    //Add a member to the group:
    public void joinGroup(GroupMember member) {
        //TODO: DB: Implement addMember in GroupRepository
        groupRepository.addMember(member);
    }

    public PostsToDisplayForUserDTO getAllPostsOfGroupToDisplayForUser(Long userId, Long groupId){
        PostsToDisplayForUserDTO pdto = new PostsToDisplayForUserDTO();
        try {
            List<PostResultSetMapping> posts = postRepository.getAllPostsOfGroupToDisplayForUser(userId, groupId);
            System.out.println(posts);
            pdto.createRecursiveDTOStructure(posts);
            pdto.ok();
        } catch(Exception e){
            e.printStackTrace();
            pdto.error();
        }
        return pdto;
    }
}

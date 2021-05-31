package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.model.Group;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.PostRepository;
import com.cs334.project3.repo.PostResultSetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicDisplayService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PostRepository postRepository;

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

    public PostsToDisplayForUserDTO getAllPostsToDisplayForUser(Long userId){
        PostsToDisplayForUserDTO pdto = new PostsToDisplayForUserDTO();
        try {
            List<PostResultSetMapping> posts = postRepository.getAllPostsToDisplayForUser(userId);
            //System.out.println(posts);
            pdto.createRecursiveDTOStructure(posts);
            pdto.ok();
        } catch(Exception e){
            e.printStackTrace();
            pdto.error();
        }
        return pdto;
    }

    public PostsToDisplayForUserDTO getAllPostsOfGroupToDisplayForUser(Long userId, Long groupId){
        PostsToDisplayForUserDTO pdto = new PostsToDisplayForUserDTO();
        try {
            List<PostResultSetMapping> posts = postRepository.getAllPostsOfGroupToDisplayForUser(userId, groupId);
            //System.out.println(posts);
            pdto.createRecursiveDTOStructure(posts);
            pdto.ok();
        } catch(Exception e){
            e.printStackTrace();
            pdto.error();
        }
        return pdto;
    }


}

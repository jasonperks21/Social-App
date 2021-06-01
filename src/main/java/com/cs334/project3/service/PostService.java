package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.repo.CategoryRepository;
import com.cs334.project3.repo.GroupMemberRepository;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.PostRepository;
import com.cs334.project3.repo.resultset.PostResultSetMapping;
import com.cs334.project3.requestbody.PostRequestBody;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Get all posts to display for a specific user.
     * This user is by definition a member of all the groups within the posts.
     * @param userId The user ID to display for.
     * @return All posts.
     */
    public List<PostDTO> getAllPostsForUser(Long userId){
        List<PostResultSetMapping> posts = postRepository.getAllPostsToDisplayForUser(userId);
        List<PostDTO> dto = PostDTOProcessor.createRecursiveDTOStructure(posts);
        return dto;
    }

    /**
     * Get all posts of a specific group to display for specific user.
     * This user is by definition a member of all the groups within the posts.
     * @param userId The user ID to display for.
     * @param groupId The group ID to get.
     * @return All posts of this group. There will not be any results if a member does not belong to this group.
     */
    //Get all the posts from a group to display for the user:
    public List<PostDTO> getAllPostsForUserByGroup(Long userId, Long groupId){
        List<PostResultSetMapping> posts = postRepository.getAllPostsOfGroupToDisplayForUser(userId, groupId);
        List<PostDTO> dto = PostDTOProcessor.createRecursiveDTOStructure(posts);
        return dto;
    }

    /**
     * Get all posts posted after a specific time to display for specific user.
     * This user is by definition a member of all the groups within the posts.
     * @param userId The user ID to display for.
     * @param time The time.
     * @return All posts that were posted after this time.
     */
    //Get all the posts from a group to display for the user:
    public List<PostDTO> getAllPostsForUserByTimeAfter(Long userId, ZonedDateTime time){
        List<PostResultSetMapping> posts = postRepository.getAllPostsAfterSpecifiedTimeToDisplayForUser(userId, time);
        List<PostDTO> dto = PostDTOProcessor.createRecursiveDTOStructure(posts);
        return dto;
    }

    /**
     * Get all posts posted before a specific time to display for specific user.
     * This user is by definition a member of all the groups within the posts.
     * @param userId The user ID to display for.
     * @param time The time.
     * @return All posts that were posted after this time.
     */
    //Get all the posts from a group to display for the user:
    public List<PostDTO> getAllPostsForUserByTimeBefore(Long userId, ZonedDateTime time){
        List<PostResultSetMapping> posts = postRepository.getAllPostsBeforeSpecifiedTimeToDisplayForUser(userId, time);
        List<PostDTO> dto = PostDTOProcessor.createRecursiveDTOStructure(posts);
        return dto;
    }

    /**
     * Delete a post.
     * @param postId The post ID.
     */
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

    /**
     * Create a post. This can either by a reply or a standalone post. replyId must be null if it is not a reply.
     * @param params The post parameters.
     * @return The post DTO. Note that replies have no elements. This must be updated on the front end.
     */
    public PostDTO createPost(PostRequestBody params){
        GroupMember gm = groupMemberRepository.getUserGroupMembership(params.getUserId(), params.getGroupId());
        Category c = categoryRepository.getById(params.getCategoryId());
        Post p;
        if(params.getReplyId() == null){
            p = gm.postToGroup(c, params.getMessage());
        } else {
            p = gm.replyToPost(postRepository.getById(params.getReplyId()), params.getMessage());
        }
        postRepository.save(p);
        return new PostDTO( new PostResultSetMapping(gm.getGroup().getGroupName(),
                p.getTimestamp(), p.getGroup().getGroup_id(), p.getPost_id(),
                params.getReplyId(), params.getMessage(),gm.getUser().getDisplayName(),
                gm.getUser().getUser_id(),gm.getMember_id(),c.getCategoryName(),c.getCategory_id()));
    }


    private Geometry createCircle(double x, double y, double radius) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(x, y));
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createCircle();
    }
}

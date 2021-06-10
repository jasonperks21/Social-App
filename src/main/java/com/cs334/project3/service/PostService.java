package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.repo.CategoryRepository;
import com.cs334.project3.repo.GroupMemberRepository;
import com.cs334.project3.repo.GroupRepository;
import com.cs334.project3.repo.PostRepository;
import com.cs334.project3.repo.resultset.PostResultSetMapping;
import com.cs334.project3.requestbody.FilterPostsRequestBody;
import com.cs334.project3.requestbody.PostRequestBody;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
     * Delete a post. This automatically deletes all children.
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
        Category c = categoryRepository.findById(params.getCategoryId()).get();
        Post p;
        if(params.getReplyId() == null){
            p = gm.postToGroup(c, params.getMessage());
        } else {
            p = gm.replyToPost(postRepository.findById(params.getReplyId()).get(), params.getMessage());
        }
        p.setLocation(params.getLongitude(), params.getLatitude());
        postRepository.save(p);
        return new PostDTO( new PostResultSetMapping(gm.getGroup().getGroupName(),
                p.getTimestamp(), p.getGroup().getGroup_id(), p.getPost_id(),
                params.getReplyId(), params.getMessage(),gm.getUser().getDisplayName(),
                gm.getUser().getUser_id(),gm.getMember_id(),c.getCategoryName(),c.getCategory_id(), null));
    }

    /**
     * Search for posts based off filter criteria. Filter criteria can be null if filtering is not required.
     * @param userId
     * @param filterUsedId
     * @param groupId
     * @param after
     * @param time
     * @param radiusKm
     * @param longitude
     * @param latitude
     * @return A list of DTOs ready for display.
     */
    public List<PostDTO> filterPosts(Long userId, Long filterUsedId, Long groupId, Boolean after, ZonedDateTime time, Double radiusKm, Double longitude, Double latitude){
        GeometryFactory factory = new GeometryFactory();
        Point loc = (radiusKm == null) ? null : factory.createPoint(new Coordinate(longitude, latitude));
        List<PostResultSetMapping> l = postRepository.filter(userId, filterUsedId,
                groupId, time, after, radiusKm, loc);
        return PostDTOProcessor.createRecursiveDTOStructure(l);
    }


}

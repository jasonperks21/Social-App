package com.cs334.project3.dto;

import com.cs334.project3.repo.resultset.PostResultSetMapping;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {
    private String groupName;
    private Long groupId;
    private Long postId;
    private Long categoryId;
    @Setter
    private List<PostDTO> replies;
    @Setter
    private String categoryName;
    private String message;
    private Long postUserId;
    private String userDisplayName;
    private ZonedDateTime timePosted;


    public PostDTO(PostResultSetMapping post){
        groupId = post.getGroupId();
        postId = post.getPostId();
        replies = new ArrayList<>();
        categoryName = post.getCategory();
        this.message = post.getMessage();
        postUserId = post.getUserId();
        userDisplayName = post.getUserDisplayName();
        timePosted = post.getTimePosted();
        groupName = post.getGroupName();
        categoryId = post.getCategoryId();
    }

    public void addReply(PostDTO dto){
        replies.add(dto);
    }
}

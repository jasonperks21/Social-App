package com.cs334.project3.dto;

import com.cs334.project3.model.GroupMember;
import com.cs334.project3.model.Post;
import com.cs334.project3.model.User;
import com.cs334.project3.repo.PostResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {
    private Long postId;
    @Setter
    private UserDTO user;
    @Setter
    private List<PostDTO> replies;
    @Setter
    private String categoryName;
    private String message;

    public PostDTO(PostResultSetMapping post){
        postId = post.getPostId();
        user = new UserDTO(post.getUserId(), post.getUserDisplayName(), false); //to do, update admin
        replies = new ArrayList<>();
        categoryName = post.getCategory();
        this.message = post.getMessage();
    }

    public void addReply(PostDTO dto){
        replies.add(dto);
    }
}

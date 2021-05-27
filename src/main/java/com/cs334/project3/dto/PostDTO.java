package com.cs334.project3.dto;

import com.cs334.project3.model.GroupMember;
import com.cs334.project3.model.Post;
import com.cs334.project3.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class PostDTO {
    private Long postId;
    @Setter
    private UserDTO user;
    @Setter
    private PostDTO reply;
    @Setter
    private String categoryName;

    public PostDTO(Post post){
        GroupMember gm = post.getMember();
        user = new UserDTO(gm.getUser(), gm.getAdmin());
        categoryName = post.getCategory().getCategoryName();
        postId = getPostId();
    }
}

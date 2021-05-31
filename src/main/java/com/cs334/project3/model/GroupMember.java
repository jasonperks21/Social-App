package com.cs334.project3.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "group_members")
public class GroupMember {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long member_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @Column
    private Boolean admin;

    public GroupMember(Group group, User user, Boolean admin) {
        this.group = group;
        group.addMember(this);
        this.user = user;
        this.admin = admin;
        posts = new ArrayList<>();
    }

    /**
     * Add a post to this user.
     * @param post The post.
     */
    protected void addPost(Post post) {
        posts.add(post);
    }

    /**
     * Post to a group. THIS IS NOT A REPLY.
     * @param category The category.
     * @param message The message.
     * @return The Post.
     */
    public Post postToGroup(Category category, String message){
        return new Post(this.group, this, category, message);
    }

    /**
     * Reply on post.
     * @param replyingOn The post to reply on.
     * @param message The message.
     * @return The Post.
     */
    public Post replyToPost(Post replyingOn, String message){
        return new Post(replyingOn, this, message);
    }



}

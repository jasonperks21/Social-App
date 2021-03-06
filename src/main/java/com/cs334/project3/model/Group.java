package com.cs334.project3.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "postgroups")
public class Group {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long group_id;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GroupMember> members;

    @Column
    private String groupName;


    /**
     * Create a new group.
     * @param name The group's name.
     */
    public Group(String name) {
        this.groupName = name;
        posts = new ArrayList<>();
        members = new ArrayList<>();
    }

    /**
     * Add post to this group. Automatically set the post object's group to this group.
     * @param post The post.
     */
    protected void addPost(Post post){
        post.setGroup(this);
        posts.add(post);
    }

    /**
     * Add a member to this group. Automatically set the member object's group to this group.
     * @param member The member.
     */
    protected void addMember(GroupMember member){
        member.setGroup(this);
        members.add(member);
    }
}

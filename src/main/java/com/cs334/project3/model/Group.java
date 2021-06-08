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

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
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

        // group <- repo by id (group)
        // user <- repo by id (user)
        // groupmember <- new GroupMember(group, user, admin)
        // group repo save(groupmember)


    }
}

package com.cs334.project3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long post_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private GroupMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(orphanRemoval = true, mappedBy = "replied", fetch = FetchType.LAZY)
    private List<Post> replies;

    @ManyToOne( fetch = FetchType.LAZY)
    private Post replied = null;

    @Column(length = 4096)
    private String message;

    @Column
    private ZonedDateTime timestamp;

    @Setter
    @Column
    private Point location;



    /**
     * Create a post with that is NOT A REPLY. This constructor updates all relevant objects.
     * @param group The group to post on.
     * @param member The user posting.
     * @param category The category of the post.
     * @param message The message.
     */
    public Post(Group group, GroupMember member, Category category, String message) {
        this.group = group;
        group.addPost(this);
        this.member = member;
        member.addPost(this);
        this.category = category;
        category.addPost(this);
        this.replies = new ArrayList<>();
        this.message = message.substring(0, Math.min(4096, message.length())); //protect against long messages
        this.timestamp = ZonedDateTime.now();
    }

    /**
     * Create a post that replies on another post. This constructor updates all relevant objects.
     * @param replyingOn The post the user is replying on.
     * @param member The user posting.
     * @param message The message.
     */
    public Post(Post replyingOn, GroupMember member, String message) {
        this.group = replyingOn.getGroup();
        group.addPost(this);
        this.member = member;
        member.addPost(this);
        this.category = replyingOn.getCategory();
        category.addPost(this);
        this.replies = new ArrayList<>();
        this.message =  message.substring(0, Math.min(4096, message.length()));;
        replyingOn.addReply(this);
        this.timestamp = ZonedDateTime.now();
        this.replied = replyingOn;
    }


    private void addReply(Post reply){
        replies.add(reply);
    }
}

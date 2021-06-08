package com.cs334.project3.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long user_id;

    @Column
    private String displayName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    @Column(length = 60)
    private String passwordHash;
    @Column
    private String jwt_token = null;

    @OneToMany(mappedBy = "user")
    private List<GroupMember> memberships;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends;

    @OneToMany(mappedBy = "friend")
    private List<Friend> friends_of;



    protected void addFriend(Friend friend){
        friends.add(friend);
    }

    protected void addFriendOf(Friend friend){
        friends_of.add(friend);
    }

    /**
     * Construct a user.
     *
     * @param displayName Name to display.
     * @param email       User email address. Must be unique.
     * @param username    Username. Must be unique.
     * @param passwordHash   The password. This class does not handle hashing.
     */
    public User(String displayName, String email, String username, String passwordHash) {
        this.displayName = displayName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        memberships = new ArrayList<>();
        friends = new ArrayList<>();
        friends_of = new ArrayList<>();
    }

    /**
     * Add a membership to an existing group. This updates all relevant fields.
     *
     * @param group The group.
     * @param admin Whether the user has admin rights on the group.
     * @return The GroupMember object that was created.
     */
    public GroupMember addGroupMembership(Group group, Boolean admin) {
        GroupMember gm = new GroupMember(group, this, admin);
        memberships.add(gm);
        return gm;
    }
}

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
    @Column
    private String email;
    @Column //TODO: unique
    private String username;
    @Column
    private Integer passwordHash;

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
     * @param password    The password. This class does handles hashing.
     */
    public User(String displayName, String email, String username, String password) {
        this.displayName = displayName;
        this.email = email;
        this.username = username;
        this.passwordHash = password.hashCode();
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


    /**
     * Check whether the password matches the stored hash.
     *
     * @param password The password.
     * @return Whether the password matches the stored hash.
     */
    public Boolean checkPassword(String password) {
        return this.passwordHash == password.hashCode();
    }
}

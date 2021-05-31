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
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long friend_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User friend;

    /**
     * Create a friend object. This keeps track of the current user and the friend that the user has added.
     * All fields are linked accordingly.
     * @param user The user that added the friend.
     * @param friend The friend.
     */
    public Friend(User user, User friend){
        this.friend = friend;
        this.user = user;
        user.addFriend(this);
    }


}


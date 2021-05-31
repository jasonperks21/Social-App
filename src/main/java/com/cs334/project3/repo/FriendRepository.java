package com.cs334.project3.repo;

import com.cs334.project3.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "select f from Friend f, User u " +
            "where u.user_id = :uid and " +
            "f.user.user_id = u.user_id")
    public List<Friend> getAllFriendsOfUser(@Param("uid") Long userId);
}

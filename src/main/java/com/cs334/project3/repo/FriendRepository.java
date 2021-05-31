package com.cs334.project3.repo;

import com.cs334.project3.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f, User u " +
            "where u.user_id = :uid and " +
            "f.friend.user_id = u.user_id and " +
            "f.user.user_id = u.user_id")
    public List<Friend> getAllFriendsOfUser(@Param("uid") Long userId);

    @Query("select case when (count(f)>0) then true else false end " +
            "from Friend f, User u " +
            "where u.user_id = :uid and " +
            "f.user.user_id = u.user_id and " +
            "f.friend.user_id = :fid")
    public Boolean doesFriendshipExist(@Param("uid") Long userId, @Param("fid") Long friendId);
}

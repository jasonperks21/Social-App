package com.cs334.project3.repo;

import com.cs334.project3.model.Post;
import com.cs334.project3.repo.resultset.PostResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Get all posts for a user to see where the user is a member on the groups..
     * @param userId The ID of the user to display for.
     * @return All the posts.
     */
    @Query(value = "SELECT new com.cs334.project3.repo.resultset.PostResultSetMapping(p.group.groupName, p.timestamp," +
            "p.group.group_id,p.post_id, p.replied.post_id, p.message, p.member.user.displayName, " +
            "p.member.user.user_id, gm.member_id, c.categoryName, c.category_id)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "c.category_id = p.category.category_id\n" +
            "order by p.timestamp")
    public List<PostResultSetMapping> getAllPostsToDisplayForUser(@Param("uid") Long userId);

    /**
     * Get posts for a user to see that is posted on a specific group.
     * @param userId The ID of the user to display for.
     * @param groupId The ID of the group to find the posts on.
     * @return All the posts meeting the sorting requirement.
     */
    @Query(value = "SELECT new com.cs334.project3.repo.resultset.PostResultSetMapping(p.group.groupName, " +
            "p.timestamp,p.group.group_id,p.post_id, p.replied.post_id, p.message, p.member.user.displayName, " +
            "p.member.user.user_id, gm.member_id, c.categoryName, c.category_id)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "p.group.group_id = :gid and\n" +
            "c.category_id = p.category.category_id\n" +
            "order by p.timestamp")
    public List<PostResultSetMapping> getAllPostsOfGroupToDisplayForUser(@Param("uid") Long userId, @Param("gid") Long groupId);

    /**
     * Get posts for a user to see where only a specific user has posted.
     * @param userId The ID of the user to display for.
     * @param postUserId The ID of the user that made the posts.
     * @return All the posts meeting the sorting requirement.
     */
    @Query(value = "SELECT new com.cs334.project3.repo.resultset.PostResultSetMapping(p.group.groupName, " +
            "p.timestamp,p.group.group_id,p.post_id, p.replied.post_id, p.message, p.member.user.displayName, " +
            "p.member.user.user_id, gm.member_id, c.categoryName, c.category_id)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "c.category_id = p.category.category_id and\n" +
            "p.member.user.user_id = :puid\n" +
            "order by p.timestamp")
    public List<PostResultSetMapping> getAllPostsOfSpecificUserToDisplayForUser(@Param("uid") Long userId, @Param("puid") Long postUserId);

    /**
     * Get posts for a user to see that was only posted AFTER a specific time.
     * @param userId The ID of the user to display for.
     * @param time The time to check for.
     * @return All the posts meeting the sorting requirement.
     */
    @Query(value = "SELECT new com.cs334.project3.repo.resultset.PostResultSetMapping(p.group.groupName, " +
            "p.timestamp,p.group.group_id,p.post_id, p.replied.post_id, p.message, p.member.user.displayName, " +
            "p.member.user.user_id, gm.member_id, c.categoryName, c.category_id)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "c.category_id = p.category.category_id and\n" +
            "p.timestamp > :time\n" +
            "order by p.timestamp")
    public List<PostResultSetMapping> getAllPostsAfterSpecifiedTimeToDisplayForUser(@Param("uid") Long userId, @Param("time")ZonedDateTime time);

    /**
     * Get posts for a user to see that was only posted BEFORE a specific time.
     * @param userId The ID of the user to display for.
     * @param time The time to check for.
     * @return All the posts meeting the sorting requirement.
     */
    @Query(value = "SELECT new com.cs334.project3.repo.resultset.PostResultSetMapping(p.group.groupName, " +
            "p.timestamp,p.group.group_id,p.post_id, p.replied.post_id, p.message, p.member.user.displayName, " +
            "p.member.user.user_id, gm.member_id, c.categoryName, c.category_id)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "c.category_id = p.category.category_id and\n" +
            "p.timestamp < :time\n" +
            "order by p.timestamp")
    public List<PostResultSetMapping> getAllPostsBeforeSpecifiedTimeToDisplayForUser(@Param("uid") Long userId, @Param("time")ZonedDateTime time);



}

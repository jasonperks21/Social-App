package com.cs334.project3.repo;

import com.cs334.project3.model.Group;
import com.cs334.project3.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT new com.cs334.project3.repo.PostResultSetMapping(p.group.groupName, p.timestamp,p.group.group_id,p.post_id, p.replied.post_id, p.message, p.timestamp, p.member.user.displayName, p.member.user.user_id, gm.member_id, c.categoryName)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "c.category_id = p.category.category_id\n" +
            "order by p.post_id")
    public List<PostResultSetMapping> getAllPostsToDisplayForUser(@Param("uid") Long userId);

    @Query(value = "SELECT new com.cs334.project3.repo.PostResultSetMapping(p.group.groupName, p.timestamp,p.group.group_id,p.post_id, p.replied.post_id, p.message, p.timestamp, p.member.user.displayName, p.member.user.user_id, gm.member_id, c.categoryName)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "p.group.group_id = :gid and\n" +
            "c.category_id = p.category.category_id\n" +
            "order by p.post_id")
    public List<PostResultSetMapping> getAllPostsOfGroupToDisplayForUser(@Param("uid") Long userId, @Param("gid") Long groupId);

}

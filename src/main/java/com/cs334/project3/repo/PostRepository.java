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

    @Query(value = "SELECT new com.cs334.project3.repo.PostResultSetMapping(p.post_id, p.replied.post_id, p.message, p.timestamp, u.displayName, u.user_id, gm.member_id, c.categoryName)\n" +
            "FROM Post p, Group pg, GroupMember gm, User u, Category c\n" +
            "where u.user_id = gm.user.user_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid and\n" +
            "c.category_id = p.category.category_id\n" +
            "order by p.post_id")
    public List<PostResultSetMapping> getAllPostsToDisplayForUser(@Param("uid") Long userId);

    @Query(value = "SELECT p FROM Post p, Post pr, Group pg, GroupMember gm, User u join fetch p.replied rep " +
            "where u.user_id = gm.user.user_id and\n" +
            "rep.post_id = p.replied.post_id and\n" +
            "gm.group.group_id = pg.group_id and\n" +
            "p.group.group_id = pg.group_id and\n" +
            "gm.user.user_id = :uid \n" +
            "order by p.post_id ")
    public List<Post> getAllPostsToDisplayForUserHQL(@Param("uid") Long userId);
}

package com.cs334.project3.repo;

import com.cs334.project3.model.Group;
import com.cs334.project3.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT p.* FROM posts p, postgroups pg, group_members gm, users u \n" +
            "where u.user_id = gm.user_id and\n" +
            "gm.group_id = pg.group_id and\n" +
            "p.group_id = pg.group_id and\n" +
            "gm.user_id = :uid \n" +
            "order by post_id", nativeQuery = true)
    public List<Post> getAllPostsToDisplayForUser(@Param("uid") Long userId);
}

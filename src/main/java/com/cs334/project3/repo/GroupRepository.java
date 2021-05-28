package com.cs334.project3.repo;

import com.cs334.project3.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select g.* from postgroups g, group_members gm, users u \nwhere g.group_id = gm.group_id and gm.user_id = u.user_id and u.user_id = :uid",
            nativeQuery = true)
    public List<Group> getALlGroupsThatUserIsMemberOf(@Param("uid") Long userId);
}

package com.cs334.project3.repo;

import com.cs334.project3.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g, GroupMember gm, User u " +
            "where g.group_id = gm.group.group_id and " +
            "gm.user.user_id = u.user_id and u.user_id = :uid")
    public List<Group> getALlGroupsThatUserIsMemberOf(@Param("uid") Long userId);

    // group <- groupRepo.getById(groupid)
    // gm <- group.getMembers()
    // user <- gm.getUser()

}

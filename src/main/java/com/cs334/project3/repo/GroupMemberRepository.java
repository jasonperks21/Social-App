package com.cs334.project3.repo;

import com.cs334.project3.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    /**
     * Get the GroupMember object associated with a user and a group.
     * WARNING. THIS WILL THROW A NON-UNIQUE RESULT EXCEPTION IF THERE ARE MORE THAN ONE MATCHES.
     * @param userId The user ID.
     * @param groupId The group ID.
     * @return The GroupMember object.
     */
    @Query("select gm from GroupMember gm, User u " +
            "where gm.user.user_id = u.user_id and " +
            "u.user_id = :uid and " +
            "gm.group.group_id = :gid ")

    public GroupMember getUserGroupMembership(@Param("uid") Long userId, @Param("gid") Long groupId);


}

package com.cs334.project3.repo;

import com.cs334.project3.model.Group;
import com.cs334.project3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.username = :uname")
    public List<User> findUserByUsername(@Param("uname") String userName);

    @Query(value = "select u from User u where u.displayName = :dispname")
    public List<User> findUserByDispname(@Param("dispname") String displayName);

    @Query(value = "select u from User u where u.email = :email")
    public List<User> findUserByEmail(@Param("email") String email);

    @Query("select u from User u " +
            "where lower(u.displayName) like concat('%', concat(lower(:s),'%')) or " +
            "lower(u.username) like concat('%', concat(lower(:s),'%')) or " +
            "lower(u.email) like concat('%', concat(lower(:s),'%'))")
    public List<User> searchForUser(@Param("s") String search);
}
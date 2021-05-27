package com.cs334.project3.repo;

import com.cs334.project3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    @Query("select t from User t")
    public List<User> getAllUsers();

}

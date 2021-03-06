package com.cs334.project3.repo;

import com.cs334.project3.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer>{

    @Query("select t from Test t")
    public List<Test> getAll();

}

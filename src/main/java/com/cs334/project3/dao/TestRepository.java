package com.cs334.project3.dao;

import com.cs334.project3.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<Test,Integer> {

    @Query("select t from Test t")
    public List<Test> getAll();

}

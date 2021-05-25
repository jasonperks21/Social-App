package com.cs334.project3.service;

import com.cs334.project3.dao.TestRepository;
import com.cs334.project3.dao.UserRepository;
import com.cs334.project3.model.Test;
import com.cs334.project3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService implements ITestService{
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    // TEST CODE

    @Override
    public List<Test> getAll() {
        return testRepository.getAll();
    }

    @Override
    public void insert(Test t) {
        testRepository.save(t);
    }

    // USERS

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }
}


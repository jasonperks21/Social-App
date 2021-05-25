package com.cs334.project3.service;

import com.cs334.project3.model.Test;
import com.cs334.project3.model.User;

import java.util.List;

public interface ITestService {
    public List<Test> getAll();
    public List<User> getAllUsers();
    public void insert(Test t);
    public void insert(User user);
}


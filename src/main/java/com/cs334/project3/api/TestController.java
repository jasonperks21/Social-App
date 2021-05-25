package com.cs334.project3.api;

import com.cs334.project3.model.Test;
import com.cs334.project3.model.User;
import com.cs334.project3.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private ITestService testService;

    // TESTING CODE

    @GetMapping("/test")
    public List<Test> getAll(){
        return testService.getAll();
    }

    @PostMapping(value = "/add")
    public String add(@RequestBody Test t){
        testService.insert(t);
        return "THANKS FOR POSTING " + t.text;
    }

    // USERS

    @GetMapping("/user/list")
    public List<User> getAllUsers(){
        return testService.getAllUsers();
    }

    @PostMapping(value = "/user/add")
    public String addUser(@RequestBody User user){
        testService.insert(user);
        return "Password: "+user.getPwd();
    }
}

package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.GroupsThatUserIsMemberOfDTO;
import com.cs334.project3.dto.PostsToDisplayForUserDTO;
import com.cs334.project3.model.Test;
import com.cs334.project3.service.BasicDisplayService;
import com.cs334.project3.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private ITestService testService;

    @Autowired
    DataGenerator dataGenerator;

    @Autowired
    BasicDisplayService groupService;

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

    @GetMapping("/gendata")
    public String genData(){
        dataGenerator.genData();
        return "GENERATED SOME TEST DATA";
    }

    @GetMapping("/groups/{userId}")
    public GroupsThatUserIsMemberOfDTO getGroupsForUser(@PathVariable Long userId){
        return groupService.getGroupsWhereUserIsMember(userId);
    }

    @GetMapping("/posts/{userId}")
    public PostsToDisplayForUserDTO getPostsForUser(@PathVariable Long userId){
        return groupService.getAllPostsToDisplayForUser(userId);
    }

    @GetMapping("/posts/{userId}/{groupId}")
    public PostsToDisplayForUserDTO getPostsOfGroupForUser(@PathVariable Long userId, @PathVariable Long groupId){
        return groupService.getAllPostsOfGroupToDisplayForUser(userId, groupId);
    }
}

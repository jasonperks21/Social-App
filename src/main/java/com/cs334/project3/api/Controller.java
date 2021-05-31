package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.GroupsThatUserIsMemberOfDTO;
import com.cs334.project3.dto.PostsToDisplayForUserDTO;
import com.cs334.project3.model.Test;
import com.cs334.project3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    
    ////////////////////////////////////////////////////////
    //// AUTOWIRED SERVICES ////////////////////////////////
    ////////////////////////////////////////////////////////

    @Autowired
    private ITestService testService;

    @Autowired
    BasicDisplayService basicDisplayService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private PostService postService;

    @Autowired
    DataGenerator dataGenerator;

    ////////////////////////////////////////////////////////
    //// TEST CODE /////////////////////////////////////////
    ////////////////////////////////////////////////////////

    @GetMapping("/test")
    public List<Test> getAll(){
        return testService.getAll();
    }

    @PostMapping(value = "/add")
    public String add(@RequestBody Test t){
        testService.insert(t);
        return "THANKS FOR POSTING " + t.text;
    }

    ////////////////////////////////////////////////////////
    //// REAL CODE /////////////////////////////////////////
    ////////////////////////////////////////////////////////

    @GetMapping("/gendata")
    public String genData(){
        dataGenerator.genData();
        return "GENERATED SOME TEST DATA";
    }

    @GetMapping("/groups/{userId}")
    public GroupsThatUserIsMemberOfDTO getGroupsForUser(@PathVariable Long userId){
        return groupMemberService.getGroupsWhereUserIsMember(userId);
    }

    @GetMapping("/posts/{userId}")
    public PostsToDisplayForUserDTO getPostsForUser(@PathVariable Long userId){
        return postService.getAllPostsToDisplayForUser(userId);
    }

    @GetMapping("/posts/{userId}/{groupId}")
    public PostsToDisplayForUserDTO getPostsOfGroupForUser(@PathVariable Long userId, @PathVariable Long groupId){
        return postService.getAllPostsOfGroupToDisplayForUser(userId, groupId);
    }

    ////////////////////////////////////////////////////////
    //// USER METHODS //////////////////////////////////////
    ////////////////////////////////////////////////////////

    

}

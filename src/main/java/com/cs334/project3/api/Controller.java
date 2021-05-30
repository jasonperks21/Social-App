package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.GroupsThatUserIsMemberOfDTO;
import com.cs334.project3.dto.PostsToDisplayForUserDTO;
import com.cs334.project3.model.*;
import com.cs334.project3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
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

    ////////////////////Controller for groups/////////////////////
    @PostMapping("/groups/{group}")
    public void addGroup(@PathVariable Group group) {
        groupService.createGroup(group);
    }

    @DeleteMapping("/groups/{group}")
    public void removeGroup(@PathVariable Group group) {
        groupService.deleteGroup(group);
    }

    @GetMapping("/groups/{groupId}")
    public boolean groupExists(@PathVariable Long groupId) {
        return groupService.groupIdExists(groupId);
    }

    @GetMapping("/groups/{groupId}")
    public Group findGroupById(@PathVariable Long groupId) {
        return groupService.getGroupById(groupId);
    }

    @GetMapping("/groups/{groupName}")
    public Group findGroupByName(@PathVariable String groupName) {
        return groupService.getGroupByName(groupName);
    }

    @GetMapping("/groups/{member}")
    public void addMemberToGroup(@PathVariable GroupMember member) {
        groupService.joinGroup(member);
    }

    ////////////////////Controller for posts/////////////////////
    @GetMapping("/posts/{userId}")
    public PostsToDisplayForUserDTO getPostsForUser(@PathVariable Long userId){
        return postService.getAllPostsToDisplayForUser(userId);
    }

    @GetMapping("/posts/{userId}/{groupId}")
    public PostsToDisplayForUserDTO getPostsOfGroupForUser(@PathVariable Long userId, @PathVariable Long groupId){
        return postService.getAllPostsOfGroupToDisplayForUser(userId, groupId);
    }

    @PostMapping("/posts/{post}")
    public void addPost(@PathVariable Post post) {
        postService.createPost(post);
    }

    @DeleteMapping("/posts/{post}")
    public void deletePost(@PathVariable Post post) {
        postService.deletePost(post);
    }

    @GetMapping("/posts/{postId}")
    public boolean postExists(@PathVariable Long postId) {
        return postService.postIdExists(postId);
    }

    @GetMapping("/posts/{postId}")
    public Post findPostById(@PathVariable Long postId) {
        return postService.getPostByID(postId);
    }

    @GetMapping("/posts/{category}")
    public List<Post> findPostByCategory(@PathVariable Category category) {
        return  postService.getPostByCategory(category);
    }

    @GetMapping("/posts/{member}")
    public List<Post> findPostByMember(@PathVariable GroupMember member) {
        return postService.getPostByMember(member);
    }

    @GetMapping("/posts/{timestamp}")
    public List<Post> findPostByTime(@PathVariable ZonedDateTime timestamp) {
        return postService.getPostByTime(timestamp);
    }

    @GetMapping("/posts/{group}")
    public List<Post> findPostByGroup(@PathVariable Group group) {
        return postService.getPostByGroup(group);
    }

    //Find post by location:
    //TODO: later

    @PostMapping("/posts/{post}")
    public void comment(@PathVariable Post post) {
        postService.addComment(post);
    }
}

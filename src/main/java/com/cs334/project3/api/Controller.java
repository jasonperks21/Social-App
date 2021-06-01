package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    /*
    ////////////////////Controller for groups/////////////////////
    @PostMapping("/groups/{group}")
    public void addGroup(@PathVariable Group group) {
        groupService.createGroup(group);
    }

    @DeleteMapping("/groups/{group}")
    public void removeGroup(@PathVariable Group group) {
        groupService.deleteGroup(group);
    }

    @DeleteMapping("/groups/{group}")
    public void removeGroupById(@PathVariable Long group_id) {
        groupService.deleteGroupById(group_id);
    }

    @GetMapping("/groups/{groupId}")
    public boolean groupExists(@PathVariable Long groupId) {
        return groupService.groupIdExists(groupId);
    }

    @GetMapping("/groups/{groupId}")
    public GroupDTO findGroupById(@PathVariable Long groupId, Long user_id) {
        return groupService.getGroupById(groupId, user_id);
    }

    @GetMapping("/groups/{groupName}")
    public GroupDTO findGroupByName(@PathVariable String groupName, Long user_id) {
        return groupService.getGroupByName(groupName, user_id);
    }

    @PutMapping("/groups/{member}")
    public void addMemberToGroup(@PathVariable Long group_id, Long user_id) {
        groupService.joinGroup(group_id,user_id);
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
    public PostDTO findPostByCategory(@PathVariable Category category) {
        return postService.getPostByCategory(category);
    }

    @GetMapping("/posts/{member}")
    public PostDTO findPostByMember(@PathVariable GroupMember member) {
        return postService.getPostByMember(member);
    }

    @GetMapping("/posts/{timestamp}")
    public PostDTO findPostByTime(@PathVariable ZonedDateTime timestamp) {
        return postService.getPostByTime(timestamp);
    }

    @GetMapping("/posts/{group}")
    public PostDTO findPostByGroup(@PathVariable Group group) {
        return postService.getPostByGroup(group);
    }

    //Find post by location:
    //TODO: later

    @PutMapping("/posts/{post}")
    public void comment(@PathVariable Post post) {
        postService.addComment(post);
    }

    */
    ////////////////////Controller for users/////////////////////
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserTransferObjectDTO dto = userService.getUserById(userId);
        if (dto.getStatus().equals("ok")){
            return new ResponseEntity<>(dto.getData(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/users/exists/{userId}")
    public ResponseEntity<Boolean> userIdExists(@PathVariable Long userId){
        boolean exists = userService.userIdExists(userId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>("Successfully deleted user with ID "+userId, HttpStatus.OK);
    }

    @PostMapping(value="/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>("Successfully added "+user.getUsername(),HttpStatus.CREATED);
    }
}

// This class extends RuntimeException and is used to return 404.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException{
}

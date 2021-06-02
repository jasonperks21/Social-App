package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.requestbody.PostRequestBodyMapping;
import com.cs334.project3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PostRemove;
import java.util.List;

@RestController
public class Controller {
    
    ////////////////////////////////////////////////////////
    //// AUTOWIRED SERVICES ////////////////////////////////
    ////////////////////////////////////////////////////////


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
    private CategoryService categoryService;

    @Autowired
    DataGenerator dataGenerator;

    ////////////////////////////////////////////////////////
    //// REAL CODE /////////////////////////////////////////
    ////////////////////////////////////////////////////////

    @GetMapping("/gendata")
    public String genData(){
        dataGenerator.genData();
        return "GENERATED SOME TEST DATA";
    }

    @GetMapping("/groups/{userId}")
    //TODO: update to response entity MARCO
    public GroupsThatUserIsMemberOfDTO getGroupsForUser(@PathVariable Long userId){
        return groupMemberService.getGroupsWhereUserIsMember(userId);
    }

    ////////////////////Controller for groups/////////////////////
    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGroup(@RequestBody GroupRequestBodyMapping ids) {
        try {
            User user = userService.getUserById(ids.getUserId());
            Group g = new Group(ids.getGroupName());
            GroupMember gm = new GroupMember(g, user, true);
            groupService.saveGroup(g);
            groupMemberService.saveMember(gm);
        } catch(Exception e) {
            throw new InternalServerErrorException("Exception raised trying to create group");
        }
    }

    @DeleteMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@RequestBody GroupRequestBodyMapping ids) {
        try {
            GroupMember gm = groupMemberService.getGroupMembership(ids.getUserId(), ids.getGroupId());

            if (gm.getAdmin()) {
                Group g = gm.getGroup();
                groupService.deleteGroup(g);
            }
        } catch(Exception e) {
            throw new InternalServerErrorException("Exception raised trying to delete group");
        }
    }

    /*
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
    */
    ////////////////////Controller for groupmembers//////////////
    @PostMapping("/groupmember")
    public ResponseEntity<GroupMembersDTO> addGroupMemberToGroupById(@RequestBody Long userId, Long groupId, boolean admin){
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO == null){
            throw new ResourceNotFoundException("No user with user ID "+userId+" exists");
        } else {
            try{
                GroupMembersDTO gmdto = groupService.joinGroup(groupId, userId, admin);
                return new ResponseEntity<>(gmdto, HttpStatus.OK);
            } catch(Exception e){
                throw new MethodNotAllowedException("User "+userId+" could not be added to the group");
            }
        }
    }




    ////////////////////Controller for posts/////////////////////
    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsForUser(@PathVariable Long userId){
        //TODO: Exception handling
        List<PostDTO> dto = postService.getAllPostsForUser(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/posts/{userId}/{groupId}")
    public ResponseEntity<List<PostDTO>> getPostsOfSpecificGroupForUser(@PathVariable Long userId,@PathVariable Long groupId){
        //TODO: Exception handling
        List<PostDTO> dto = postService.getAllPostsForUserByGroup(userId, groupId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
/*
    @GetMapping("/posts/{userId}/{groupId}")
    public PostsToDisplayForUserDTO getPostsOfGroupForUser(@PathVariable Long userId, @PathVariable Long groupId){
        return postService.getAllPostsOfGroupToDisplayForUser(userId, groupId);
    }

    */
    @PostMapping("/posts")
    public void addPost(@RequestBody PostRequestBody ids) {
        //TODO: TRY CATCH

    }
    /*

    @DeleteMapping("/posts/{post}")
    public void deletePost(@PathVariable Post post) {
        postService.deletePost(post);
    }

    @GetMapping("/posts/{postId}")
    public boolean postExists(@PathVariable Long postId) {
        return postService.postIdExists(postId);
    }

    @GetMapping("/posts/{postId})
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
//    @GetMapping(value="/users", params="uid")
//    public ResponseEntity<UserDTO> getUserById(@RequestParam Long uid){
//        UserDTO userDTO;
//        try{
//            userDTO = userService.getUserById(uid);
//            return new ResponseEntity<>(userDTO, HttpStatus.OK);
//        } catch(Exception e){
//            throw new ResourceNotFoundException("No user with user ID "+uid+" exists");
//        }
//    }

    @GetMapping(value="/users", params="uname")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String uname){
        UserDTO userDTO = userService.getUserByUsername(uname);
        if (userDTO!=null){
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No user with username "+uname+" exists");
        }
    }

    public Boolean userIdExists(Long userId){
        boolean exists = userService.userIdExists(userId);
        return exists;
    }

//    @DeleteMapping(value="/users", params="uid")
//    public ResponseEntity<String> deleteUserById(@RequestParam Long uid){
//        if(userIdExists(uid)){
//            UserDTO userDTO = userService.getUserById(uid);
//            String dispname = userDTO.getDisplayName();
//            userService.deleteUserById(uid);
//            return new ResponseEntity<>("Successfully deleted user "+dispname+" from the database", HttpStatus.OK);
//        } else {
//            throw new ResourceNotFoundException("No user with user ID "+uid+" exists");
//        }
//
//    }

    @PostMapping(value="/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addUser(@RequestBody User user){
        try{
            userService.addUser(user);
            return new ResponseEntity<>("Successfully added "+user.getDisplayName(),HttpStatus.CREATED);
        } catch(Exception e){
            throw new InternalServerErrorException("Exception raised trying to insert user "+user.getUsername()+" - maybe the DB is down?");
        }
    }
}

// 404 NOT FOUND Error
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends ResponseStatusException{
    public ResourceNotFoundException(){
        super(HttpStatus.NOT_FOUND);
    }
    public ResourceNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}

// 405 METHOD NOT ALLOWED Error
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
class MethodNotAllowedException extends ResponseStatusException {
    public MethodNotAllowedException(){
        super(HttpStatus.METHOD_NOT_ALLOWED);
    }
    public MethodNotAllowedException(String message){
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
}

// 500 INTERNAL SERVER ERROR Error
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerErrorException extends ResponseStatusException {
    public InternalServerErrorException(){
        super(HttpStatus.METHOD_NOT_ALLOWED);
    }
    public InternalServerErrorException(String message){
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
}
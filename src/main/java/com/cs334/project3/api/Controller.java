package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.requestbody.*;
import com.cs334.project3.service.*;
import com.cs334.project3.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PostRemove;
import java.util.ArrayList;
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
    private FriendService friendService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    DataGenerator dataGenerator;

    @GetMapping("/gendata")
    public String genData(){
        dataGenerator.genData();
        return "GENERATED SOME TEST DATA";
    }

    @GetMapping("/groups/{userId}")
    //TODO: Marco: update to response entity
    public ResponseEntity<List<GroupDTO>> getGroupsForUser(@PathVariable Long userId){
        //TODO: Dom: Error checking
        List<GroupDTO> gDTOList = groupMemberService.getGroupsWhereUserIsMember(userId);
        return new ResponseEntity<>(gDTOList, HttpStatus.OK);
    }

    ////////////////////Controller for groups/////////////////////
    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupRequestBodyMapping ids) {
        GroupDTO groupDTO;
        try {
            groupDTO = groupService.createGroup(ids);
            return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
        } catch(Exception e) {
            throw new InternalServerErrorException("Exception raised trying to create group");
        }
    }

    @DeleteMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GroupDTO> deleteGroup(@RequestBody GroupRequestBodyMapping ids) {
        try {
            GroupDTO gDTO = groupService.deleteGroup(ids);
            return new ResponseEntity<>(gDTO, HttpStatus.OK);
        } catch(Exception e) {
            throw new InternalServerErrorException("Exception raised trying to delete group");
        }
    }

    ////////////////////Controller for groupmembers//////////////
    @PostMapping("/groupmember")
    public ResponseEntity<GroupMembersDTO> addGroupMemberToGroupById(@RequestBody GroupRequestBodyMapping grbm){
        UserDTO userDTO = userService.getUserById(grbm.getUserId());
        if (userDTO == null){
            throw new ResourceNotFoundException("No user with user ID "+grbm.getUserId()+" exists");
        } else {
            try{
                GroupMembersDTO gmdto = groupService.joinGroup(grbm.getGroupId(), grbm.getUserId(), grbm.isAdmin());
                return new ResponseEntity<>(gmdto, HttpStatus.CREATED);
            } catch(Exception e){
                throw new InternalServerErrorException("User "+grbm.getUserId()+" could not be added to the group");
            }
        }
    }

    @GetMapping(value="/groupmember", params="gid")
    public ResponseEntity<List<GroupMembersDTO>> getGroupMembersById(@RequestParam Long gid){
        List<GroupMembersDTO> gmDTOList;
        try{
            gmDTOList = groupMemberService.getGroupMembersByGroupId(gid);
            return new ResponseEntity<>(gmDTOList, HttpStatus.OK);
        } catch(Exception e){
            //TODO: Dom: Distinguish exception types
            throw new InternalServerErrorException("Group members for group "+gid+" could not be retrieved");
        }
    }
    /*
    @PutMapping("/groupmember")
    public ResponseEntity<GroupMembersDTO> updateGroupAdminByUser(@RequestBody GroupRequestBodyMapping grbm){
        GroupMembersDTO gmDTO;
        try{
            gmDTO = groupMemberService.updateGroupAdminByUserId(grbm.getUserId(), grbm.getGroupId(), grbm.isAdmin());
            return new ResponseEntity<>(gmDTO, HttpStatus.OK);
        } catch(Exception e){
            //TODO: Dom: Distinguish exception types
            throw new InternalServerErrorException("Something went wrong, could not update admin");
        }
    }

    @DeleteMapping(value="/groupmember",params={"uid","gid"})
    public ResponseEntity<GroupMembersDTO> deleteGroupMemberById(@RequestParam Long uid, Long gid){
        GroupMembersDTO gmDTO;
        try{
            gmDTO = groupMemberService.deleteGroupMemberById(uid, gid);
            return new ResponseEntity<>(gmDTO, HttpStatus.OK);
        } catch(Exception e){
            //TODO: Dom: Distinguish exception types
            throw new InternalServerErrorException("Something went wrong, could not delete groupmember");
        }
    }
    */

    ////////////////////Controller for categories/////////////////////
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOList;
        try {
            categoryDTOList = categoryService.getCategories();
            return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerErrorException("Categories could not be retrieved");
        }
    }


    ////////////////////Controller for friends//////////////
    @PostMapping("/friends")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FriendDTO> addingFriends(@RequestBody FriendRequestBody ids) {
        FriendDTO friendDTO;
        try {
            friendDTO = friendService.addFriend(ids);
            return new ResponseEntity<>(friendDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new InternalServerErrorException("Exception raised trying to add friend");
        }
    }

    @DeleteMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@RequestBody FriendRequestBody ids) {
        try {
            Friend friend = friendService.getFriendById(ids.getUserId(), ids.getFriendId());
            friendService.deleteFriend(friend);
        } catch (Exception e) {
            throw new InternalServerErrorException("Exception raised trying to delete friend");
        }
    }

    @GetMapping(value="/friends", params="userId")
    public ResponseEntity<List<FriendDTO>> getFriendsOfUser(@RequestParam Long userId) {
        List<FriendDTO> friendDTOList;
        try {
            friendDTOList = friendService.getFriendsByUserId(userId);
            return new ResponseEntity<>(friendDTOList, HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerErrorException("Friends for " + userId + " could not be retrieved");
        }
    }

    ////////////////////Controller for posts/////////////////////
    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsForUser(@PathVariable Long userId){
        //TODO: Dom: Exception handling
        List<PostDTO> dto = postService.getAllPostsForUser(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/posts/{userId}/{groupId}")
    public ResponseEntity<List<PostDTO>> getPostsOfSpecificGroupForUser(@PathVariable Long userId,@PathVariable Long groupId){
        //TODO: Dom: Exception handling
        List<PostDTO> dto = postService.getAllPostsForUserByGroup(userId, groupId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public void addPost(@RequestBody PostRequestBody ids) {
        //TODO: Dom: Error checking
        //TODO: Marco: Try catch

    }

    ////////////////////Controller for users/////////////////////
    @GetMapping(value="/users", params="uid")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long uid){
        UserDTO userDTO;
        try{
            userDTO = userService.getUserById(uid);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch(Exception e){
            throw new ResourceNotFoundException("No user with user ID "+uid+" exists");
        }
    }

    @GetMapping(value="/users", params="q")
    public ResponseEntity<List<UserDTO>> searchForUser(@RequestParam String q){
        try{
            List<UserDTO> userDTOList = userService.searchForUser(q);
            return new ResponseEntity<>(userDTOList,HttpStatus.OK);
        } catch(Exception e) {
            throw new InternalServerErrorException("Something went wrong on our side");
        }
    }

    @DeleteMapping(value="/users", params="uid")
    public ResponseEntity<UserDTO> deleteUserById(@RequestParam Long uid){
        try{
            UserDTO userDTO = userService.deleteUserById(uid);
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        } catch(Exception e){
            throw new ResourceNotFoundException("No user with ID "+uid+" exists");
        }
    }

    @PostMapping(value="/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateUserStatus> addUser(@RequestBody CreateUserRequestBody params){
        try{
            CreateUserStatus cus = userService.createUser(params);
            if(cus.getUser()==null){
                return new ResponseEntity<>(cus, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(cus, HttpStatus.CREATED);
            }
        } catch(Exception e){
            throw new InternalServerErrorException("Something went wrong - maybe the DB is down?");
        }
    }

    @PutMapping(value="/users")
    public ResponseEntity<UserDTO> updateDispname(@RequestBody UpdateDispnameRequestBody udrb){
        try{
            UserDTO userDTO = userService.updateDispname(udrb);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch(Exception e){
            throw new ResourceNotFoundException("No such user exists");
        }
    }

    ////////////////////Registration/////////////////////
    @GetMapping("/register")
    public ResponseEntity<String> showRegistrationForm(){
        return new ResponseEntity<>("signup_form", HttpStatus.OK);
    }

    ////////////////////Login/////////////////////

}
package com.cs334.project3.api;

import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.requestbody.PostRequestBody;
import com.cs334.project3.requestbody.GroupRequestBodyMapping;
import com.cs334.project3.service.*;
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
                throw new MethodNotAllowedException("User "+grbm.getUserId()+" could not be added to the group");
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
/*
    @GetMapping("/posts/{userId}/{groupId}")
    public PostsToDisplayForUserDTO getPostsOfGroupForUser(@PathVariable Long userId, @PathVariable Long groupId){
        return postService.getAllPostsOfGroupToDisplayForUser(userId, groupId);
    }

    */
    @PostMapping("/posts")
    public void addPost(@RequestBody PostRequestBody ids) {
        //TODO: Dom: Error checking
        //TODO: Marco: Try catch

    }

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
    /*
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
    */

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
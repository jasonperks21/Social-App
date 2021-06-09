package com.cs334.project3.api;

import com.cs334.project3.auth.JwtTokenUtil;
import com.cs334.project3.datagen.DataGenerator;
import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.requestbody.*;
import com.cs334.project3.service.*;
import com.cs334.project3.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
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
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    DataGenerator dataGenerator;

    @GetMapping("/gendata")
    public String genData(){
        dataGenerator.genData();
        return "GENERATED SOME TEST DATA";
    }

    @GetMapping(value="/groups", params= "userId")
    public ResponseEntity<List<GroupDTO>> getGroupsForUser(@RequestParam Long userId, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        if(uid.equals(userId)) {
            try {
                List<GroupDTO> gDTOList = groupMemberService.getGroupsWhereUserIsMember(userId);
                return new ResponseEntity<>(gDTOList, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException("Exception raised trying to retrieve group");
            }
        }else{
            throw new ForbiddenException("user cannot access another users information");
        }
    }

    @GetMapping(value="/groups", params= "q")
    public ResponseEntity<List<GroupSearchDTO>> searchForGroups(@RequestParam String q){
        try {
            List<GroupSearchDTO> gDTOList = groupService.searchForGroup(q);
            return new ResponseEntity<>(gDTOList, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalServerErrorException("Exception raised trying to retrieve group");
        }
    }

    ////////////////////Controller for groups/////////////////////
    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupRequestBody ids) {
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
    public ResponseEntity<GroupDTO> deleteGroup(@RequestBody GroupRequestBody ids) {
        try {
            groupService.deleteGroup(ids);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            throw new InternalServerErrorException("Exception raised trying to delete group");
        }
    }

    ////////////////////Controller for groupmembers//////////////
    @PostMapping("/groupmember")
    public ResponseEntity<GroupMembersDTO> addGroupMemberToGroupById(@RequestBody GroupRequestBody grbm, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);
        Long userId = grbm.getUserId();

        if(uid.equals(userId)) {
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
        }else{
            throw new ForbiddenException("user cannot access another users information");
        }
    }

    @GetMapping(value="/groupmember", params="groupId")
    public ResponseEntity<List<GroupMembersDTO>> getGroupMembersById(@RequestParam Long groupId){
        List<GroupMembersDTO> gmDTOList;
        try{
            gmDTOList = groupMemberService.getGroupMembersByGroupId(groupId);
            return new ResponseEntity<>(gmDTOList, HttpStatus.OK);
        } catch(Exception e){
            //TODO: Dom: Distinguish exception types
            throw new InternalServerErrorException("Group members for group "+groupId+" could not be retrieved");
        }
    }

    @PutMapping("/groupmember")
    public ResponseEntity<GroupMembersDTO> updateGroupAdminByUser(@RequestBody GroupRequestBody grbm, HttpServletRequest request){
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        //Get userId from param:
        Long userId = grbm.getUserId();

        if (uid.equals(userId)) {
            GroupMembersDTO gmDTO;
            try {
                gmDTO = groupMemberService.updateGroupAdminByUserId(grbm.getUserId(), grbm.getGroupId(), grbm.isAdmin());
                return new ResponseEntity<>(gmDTO, HttpStatus.OK);
            } catch (Exception e) {
                //TODO: Dom: Distinguish exception types
                throw new InternalServerErrorException("Something went wrong, could not update admin");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    @DeleteMapping(value="/groupmember",params={"uid","gid"})
    public ResponseEntity<GroupMembersDTO> deleteGroupMemberById(@RequestParam Long uid,@RequestParam Long gid, HttpServletRequest request){
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long userId = getUserIdFromJWT(jwt);

        if (userId.equals(uid)) {
            GroupMembersDTO gmDTO;
            try{
                gmDTO = groupMemberService.deleteGroupMemberById(uid, gid);
                return new ResponseEntity<>(gmDTO, HttpStatus.OK);
            } catch(Exception e){
                //TODO: Dom: Distinguish exception types
                throw new InternalServerErrorException("Something went wrong, could not delete groupmember");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }


    ////////////////////Controller for categories/////////////////////
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
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
    public ResponseEntity<FriendDTO> addingFriends(@RequestBody FriendRequestBody ids, HttpServletRequest request) {
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        //Get userId from param:
        Long userId = ids.getUserId();

        if (uid.equals(userId)) {
            FriendDTO friendDTO;
            try {
                friendDTO = friendService.addFriend(ids);
                return new ResponseEntity<>(friendDTO, HttpStatus.CREATED);
            } catch (Exception e) {
                throw new InternalServerErrorException("Exception raised trying to add friend");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    @DeleteMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@RequestBody FriendRequestBody ids, HttpServletRequest request) {
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        //Get userId from param:
        Long userId = ids.getUserId();

        if (uid.equals(userId)) {
            try {
                Friend friend = friendService.getFriendById(ids.getUserId(), ids.getFriendId());
                friendService.deleteFriend(friend);
            } catch (Exception e) {
                throw new InternalServerErrorException("Exception raised trying to delete friend");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    @GetMapping(value="/friends", params="userId")
    public ResponseEntity<List<FriendDTO>> getFriendsOfUser(@RequestParam Long userId, HttpServletRequest request) {
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        if (uid.equals(userId)) {
            List<FriendDTO> friendDTOList;
            try {
                friendDTOList = friendService.getFriendsByUserId(userId);
                return new ResponseEntity<>(friendDTOList, HttpStatus.OK);
            } catch (Exception e) {
                throw new InternalServerErrorException("Friends for " + userId + " could not be retrieved");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    ////////////////////Controller for posts/////////////////////
    @GetMapping("/posts")
    public ResponseEntity filter(@RequestBody FilterPostsRequestBody criteria) {
        try {
            List<PostDTO> posts = postService.filterPosts(criteria);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Posts could not be retrieved");
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostRequestBody ids, HttpServletRequest request) {
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        //Get userId for param:
        Long userId = ids.getUserId();

        if (uid.equals(userId)) {
            try {
                PostDTO pdto = postService.createPost(ids);
                return new ResponseEntity<>(pdto, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException("Error in creating post: " + e.getMessage());
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    @DeleteMapping(value = "/posts", params = "postId")
    public ResponseEntity deletePost(@RequestParam Long postId){
        try{
            postService.deletePost(postId);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            throw new InternalServerErrorException("Error in deleting post: " + e.getMessage());
        }
    }

    ////////////////////Controller for users/////////////////////
    @GetMapping(value="/users", params="userId")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long userId, HttpServletRequest request){
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        if (uid.equals(userId)) {
            UserDTO userDTO;
            try {
                userDTO = userService.getUserById(userId);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } catch (Exception e) {
                throw new ResourceNotFoundException("No user with user ID " + userId + " exists");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
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

    @DeleteMapping(value="/users", params="userId")
    public ResponseEntity<UserDTO> deleteUserById(@RequestParam Long userId, HttpServletRequest request){
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        if (uid.equals(userId)) {
            try {
                UserDTO userDTO = userService.deleteUserById(userId);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } catch (Exception e) {
                throw new ResourceNotFoundException("No user with ID " + userId + " exists");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    public ResponseEntity<CreateUserStatus> addUser(CreateUserRequestBody params){
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
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateDispnameRequestBody udrb, HttpServletRequest request){
        //Get userId from token:
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);

        //Get userId for param:
        Long userId = udrb.getUserid();

        if (uid.equals(userId)) {
            try {
                UserDTO userDTO = userService.updateUser(udrb);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } catch (Exception e) {
                throw new ResourceNotFoundException("No such user exists");
            }
        } else {
            throw new ForbiddenException("User cannot access another users information");
        }
    }

    ////////////////////Register, logout/////////////////////
    @PostMapping(value="/register")
    public ResponseEntity<CreateUserStatus> registerNewUser(@RequestBody CreateUserRequestBody params){
        try{
            return addUser(params);
        } catch(Exception e){
            throw new InternalServerErrorException("Something went wrong - maybe the DB is down?");
        }
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authorization");
        String jwt = null;
        if(token.startsWith("Bearer ")){
            jwt = token.substring(7);
        } else {
            throw new ResourceNotFoundException();
        }
        Long uid = getUserIdFromJWT(jwt);
        userService.logoutUser(uid);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>("Successfully logged out user ID "+uid, HttpStatus.OK);
    }

    private Long getUserIdFromJWT(String jwt){
        return jwtTokenUtil.getUserIdFromToken(jwt);
    }
}
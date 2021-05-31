package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.model.*;
import com.cs334.project3.repo.PostRepository;
import com.cs334.project3.repo.PostResultSetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    /**
     * Create a new post:
     * @param post
     */
    public void createPost(Post post) {
        postRepository.save(post);
    }

    /**
     * Delete a post.
     * @param post
     */
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    /**
     * See if a post exists
     * @param post_id
     * @return
     */
    public boolean postIdExists(Long post_id) {
        return postRepository.existsById(post_id);
    }

    /**
     * Find post by ID.
     * @param post_id
     * @return
     */
    public Post getPostByID(Long post_id) {
        return postRepository.findById(post_id).get();
    }

    /**
     * Find post by category
     * @param category
     * @return
     */
    public PostDTO getPostByCategory(Category category) {
        //TODO: DB: implement findAllByCategory in PostRepository
        PostResultSetMapping post = postRepository.findByCategory(category).get();
        PostDTO postDTO = new PostDTO(post);
        return postDTO;
    }

    /**
     * Find post by member.
     * @param member
     * @return
     */
    public PostDTO getPostByMember(GroupMember member) {
        //TODO: DB: implement findAllByMember in PostRepository
        PostResultSetMapping post = postRepository.findByMember(member).get();
        PostDTO postDTO = new PostDTO(post);
        return  postDTO;
    }

    /**
     * Find post by time
     * @param timestamp
     * @return
     */
    public PostDTO getPostByTime(ZonedDateTime timestamp) {
        //TODO: DB: implement findAllByTime in PostRepository
        PostResultSetMapping post = postRepository.findByTime(timestamp).get();
        PostDTO postDTO = new PostDTO(post);
        return postDTO;
    }

    /**
     * Find post by group.
     * @param group
     * @return
     */
    public PostDTO getPostByGroup(Group group) {
        //TODO: DB: implement findAllByGroup in PostRepository
        PostResultSetMapping post = postRepository.findByGroup(group).get();
        PostDTO postDTO = new PostDTO(post);
        return postDTO;
    }

    //Find post by location:
    //TODO: Later.

    //TODO: Fix
    //Add comment:
    public void addComment(Post post) {
        //TODO: DB: Implement addComment in PostRepository that returns List<Post>
        postRepository.addComment(post);
    }

    // Get posts to display for user
    public PostsToDisplayForUserDTO getAllPostsToDisplayForUser(Long userId){
        PostsToDisplayForUserDTO pdto = new PostsToDisplayForUserDTO();
        try {
            List<PostResultSetMapping> posts = postRepository.getAllPostsToDisplayForUser(userId);
            System.out.println(posts);
            pdto.createRecursiveDTOStructure(posts);
            pdto.ok();
        } catch(Exception e){
            e.printStackTrace();
            pdto.error();
        }
        return pdto;
    }

    //Get all the posts from a group to display for the user:
    public PostsToDisplayForUserDTO getAllPostsOfGroupToDisplayForUser(Long userId, Long groupId){
        PostsToDisplayForUserDTO pdto = new PostsToDisplayForUserDTO();
        try {
            List<PostResultSetMapping> posts = postRepository.getAllPostsOfGroupToDisplayForUser(userId, groupId);
            System.out.println(posts);
            pdto.createRecursiveDTOStructure(posts);
            pdto.ok();
        } catch(Exception e){
            e.printStackTrace();
            pdto.error();
        }
        return pdto;
    }
}

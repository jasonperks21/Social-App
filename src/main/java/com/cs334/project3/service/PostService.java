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

    //Create a new post:
    public void createPost(Post post) {
        postRepository.save(post);
    }

    //Delete a post:
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    //See if a post exists:
    public boolean postIdExists(Long post_id) {
        return postRepository.existsById(post_id);
    }

    //Find post by ID:
    public Post getPostByID(Long post_id) {
        return postRepository.findById(post_id).get();
    }

    //Find post by category:
    public List<Post> getPostByCategory(Category category) {
        //TODO: DB: implement findAllByCategory in PostRepository that returns List<Post>
        return postRepository.findAllByCategory(category).get();
    }

    //Find post by member:
    public List<Post> getPostByMember(GroupMember member) {
        //TODO: DB: implement findAllByMember in PostRepository that returns List<Post>
        return postRepository.findAllByMember(member).get();
    }

    //Find post by time:
    public List<Post> getPostByTime(ZonedDateTime timestamp) {
        //TODO: DB: implement findAllByTime in PostRepository that returns List<Post>
        return postRepository.findAllByTime(timestamp).get();
    }

    //Find post by group:
    public List<Post> getPostByGroup(Group group) {
        //TODO: DB: implement findAllByGroup in PostRepository that returns List<Post>
        return postRepository.findAllByGroup(group).get();
    }

    //Find post by location:
    //TODO: Later.

    //Add comment:
    public void addComment(Post post) {
        //TODO: DB: Implement addComment in PostRepository that returns List<Post>
        return postRepository.addComment(post);
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

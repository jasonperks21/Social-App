package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.model.Post;
import com.cs334.project3.repo.PostRepository;
import com.cs334.project3.repo.PostResultSetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

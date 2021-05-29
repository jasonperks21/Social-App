package com.cs334.project3.service;

import com.cs334.project3.model.Post;
import com.cs334.project3.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}

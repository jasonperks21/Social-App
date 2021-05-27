package com.cs334.project3.dto;

import com.cs334.project3.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class PostsToDisplayForUser extends BaseDTO<List<PostDTO>>{

    private HashMap<Long, PostDTO> hashMap= new HashMap<>();

    public PostsToDisplayForUser(){
        super(new ArrayList<>());
    }


    public void createRecursiveDTOStructure(List<Post> posts){
        List<PostDTO> dtos = new ArrayList<>();
        Iterator<Post> it = posts.iterator();
        while (it.hasNext()) {
            Post post = it.next();
            if(post.getReplied() != null) {
                PostDTO dto = new PostDTO(post);
                hashMap.put(post.getPost_id(), dto);
                dtos.add(dto);
                it.remove();
            }
        }
        while(hashMap.size() != posts.size()){
            it = posts.iterator();
            while (it.hasNext()) {
                Post post =  it.next();
                if(hashMap.containsKey(post.getReplied().getPost_id())){
                    System.out.println("FOUND ROOT");
                }
            }
        }
        data = dtos;
    }
}

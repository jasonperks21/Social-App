package com.cs334.project3.dto;

import com.cs334.project3.repo.resultset.PostResultSetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class PostDTOProcessor {

    public static List<PostDTO> createRecursiveDTOStructure(List<PostResultSetMapping> posts){
        HashMap<Long, PostDTO> hashMap= new HashMap<>();
        List<PostDTO> dtos = new ArrayList<>();
        Iterator<PostResultSetMapping> it = posts.iterator();
        int size = posts.size();
        while (it.hasNext()) {
            PostResultSetMapping post = it.next();
            if(post.getReplyId() == null) {
                PostDTO dto = new PostDTO(post);
                if (hashMap.containsKey(post.getPostId()))
                    System.out.println("ERROR");
                hashMap.put(post.getPostId(), dto);
                dtos.add(dto);
                it.remove();
            }
        }
        while(hashMap.size() != size){
            it = posts.iterator();
            while (it.hasNext()) {
                PostResultSetMapping post =  it.next();
                if(hashMap.containsKey(post.getReplyId())){
                    PostDTO reply = new PostDTO(post);
                    PostDTO root = hashMap.get(post.getReplyId());
                    root.addReply(reply);
                    hashMap.put(post.getPostId(), reply);
                    it.remove();
                }
            }
        }
        return dtos;
    }
}

package com.cs334.project3.dto;

import com.cs334.project3.model.Post;
import com.cs334.project3.repo.PostResultSetMapping;

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
                    hashMap.put(reply.getPostId(), reply);
                    it.remove();
                }
            }
        }
        return dtos;
    }
}

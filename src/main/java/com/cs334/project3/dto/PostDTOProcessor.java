package com.cs334.project3.dto;

import com.cs334.project3.exceptions.InternalServerErrorException;
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
                    throw new InternalServerErrorException("Recursive replies could not be constructed. Post ID " + post.getPostId() + " appeared twice in the query results.");
                hashMap.put(post.getPostId(), dto);
                dtos.add(dto);
                it.remove();
            }
        }
        while(hashMap.size() != size){
            it = posts.iterator();
            if(!it.hasNext()) throw new InternalServerErrorException("Recursive replies could not be constructed. The reply list did not contain the expected amount of replies.");
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

package com.cs334.project3.service;

import com.cs334.project3.dto.FriendDTO;
import com.cs334.project3.model.Friend;
import com.cs334.project3.model.User;
import com.cs334.project3.repo.FriendRepository;
import com.cs334.project3.repo.UserRepository;
import com.cs334.project3.requestbody.FriendRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendDTO addFriend(FriendRequestBody frb) {
        Friend friend;

        try {
            User u = userRepository.getById(frb.getUserId());
            User f = userRepository.getById(frb.getFriendId());
            friend = new Friend(u, f);
            friendRepository.save(friend);
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Something went wrong on our side");
        }
        return new FriendDTO(friend);
    }

    public Friend getFriendById(Long userId, Long friendID) {
        return friendRepository.getFriendshipBetweenUsers(userId, friendID);
    }

    public void deleteFriend(Friend friend) {
        if (friendRepository.doesFriendshipExist(friend.getUser().getUser_id(), friend.getFriend().getUser_id())) {
            friendRepository.delete(friend);
        }
    }

    public List<FriendDTO> getFriendsByUserId(Long userId) {
        Friend friend = friendRepository.getById(userId);
        List<Friend> friendList = friend.getUser().getFriends();
        List<FriendDTO> friendDTOList;

        if (friendList == null) {
            throw new NullPointerException();
        } else {
            friendDTOList = new ArrayList<>();
            for (Friend test:friendList) {
                friendDTOList.add(new FriendDTO(test));
            }
        }
        return friendDTOList;
    }
}

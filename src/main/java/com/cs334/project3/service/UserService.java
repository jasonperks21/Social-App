package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.repo.UserRepository;
import com.cs334.project3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Add new user
    public void addUser(User user){
        userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUserById(Long userID){
        userRepository.deleteById(userID);
    }

    // Determine if user exists
    public boolean userIdExists(Long userID){
        return userRepository.existsById(userID);
    }

    // Get user by ID
    public UserDTO getUserById(Long userID){
        User user = userRepository.findById(userID).get();
        UserDTO userDTO = new UserDTO(user, false);
        return userDTO;
    }

    // Get user by username
    public UserDTO getUserByUsername(String uname){
        //TODO: DB : Implement findUserByUsername in userRepository that returns type Optional<User>
        User user = userRepository.findUserByUsername(uname).get();
        UserDTO userDTO = new UserDTO(user, false);
        return userDTO;
    }

    // Get user by display name
    public UserDTO getUserByDispname(String dispname){
        //TODO: DB : Implement findUserByDispname in userRepository that returns type Optional<User>
        User user = userRepository.findUserByDispname(dispname).get();
        UserDTO userDTO = new UserDTO(user, false);
        return userDTO;
    }

    // Get user by email
    public UserDTO getUserByEmail(String email){
        //TODO: DB : Implement findUserByEmail in userRepository that returns type Optional<User>
        User user = userRepository.findUserByEmail(email).get();
        UserDTO userDTO = new UserDTO(user, false);
        return userDTO;
    }

    // Change password
    public void updatePasswordById(Long userID, String newPassword){
        //TODO: DB : Implement updateUserPasswordById in userRepository
        userRepository.updateUserPasswordById(userID, newPassword);
    }

    // Change display name
    public void updateDispnameById(Long userID, String dispname){
        //TODO: DB : Implement updateUserDispnameById in userRepository
        userRepository.updateUserDispnameById(userID, dispname);
    }

}

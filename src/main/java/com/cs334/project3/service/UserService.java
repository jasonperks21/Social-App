package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.repo.UserRepository;
import com.cs334.project3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

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

    /*
    COMMENTING THESE OUT WHILE THE REPO FUNCTIONS AREN'T READY
    // Get user by username
    public UserTransferObjectDTO getUserByUsername(String uname){
        //TODO: DB : Implement findUserByUsername in userRepository that returns type Optional<User>
        UserTransferObjectDTO dto = new UserTransferObjectDTO();
        try{
            User user = userRepository.findUserByUsername(uname).get();
            UserDTO userDTO = new UserDTO(user, false);
            dto.setData(userDTO);
            dto.ok();
        } catch(Exception e){
            dto.error();
        }
        return dto;
    }

    // Get user by display name
    public UserTransferObjectDTO getUserByDispname(String dispname){
        //TODO: DB : Implement findUserByDispname in userRepository that returns type Optional<User>
        UserTransferObjectDTO dto = new UserTransferObjectDTO();
        try{
            User user = userRepository.findUserByDispname(dispname).get();
            UserDTO userDTO = new UserDTO(user, false);
            dto.setData(userDTO);
            dto.ok();
        } catch(Exception e){
            dto.error();
        }
        return dto;
    }

    // Get user by email
    public UserTransferObjectDTO getUserByEmail(String email){
        //TODO: DB : Implement findUserByDispname in userRepository that returns type Optional<User>
        UserTransferObjectDTO dto = new UserTransferObjectDTO();
        try{
            User user = userRepository.findUserByEmail(email).get();
            UserDTO userDTO = new UserDTO(user, false);
            dto.setData(userDTO);
            dto.ok();
        } catch(Exception e){
            dto.error();
        }
        return dto;
    }

    */
}

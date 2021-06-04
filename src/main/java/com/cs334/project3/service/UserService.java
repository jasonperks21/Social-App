package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.repo.UserRepository;
import com.cs334.project3.model.User;
import com.cs334.project3.requestbody.CreateUserRequestBody;
import com.cs334.project3.requestbody.UpdateDispnameRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Delete the user. Probably never necessary.
     * @param userId The user ID.
     */
    public UserDTO deleteUserById(Long userId){
        User user = userRepository.getById(userId);
        if(user!=null){
            userRepository.delete(user);
            return new UserDTO(user);
        }else{
            throw new NullPointerException("No such user exists");
        }
    }

    // Determine if user exists
    public boolean userIdExists(Long userID){
        return userRepository.existsById(userID);
    }

    // Get user by ID
    public UserDTO getUserById(Long userId){
        User user = userRepository.getById(userId);
        return new UserDTO(user);
    }

    // Get user by ID
    public User getUserByIdForGroup(Long userId){
        User user = userRepository.getById(userId);
        return user;
    }

    /**
     * Search for a user by email, display name or user name.
     * @param search The search string.
     * @return A list of users matching the search.
     */
    public List<UserDTO> searchForUser(String search){
        List<User> users = userRepository.searchForUser(search);
        List<UserDTO> dto = new ArrayList<>();
        for(User u : users){
            dto.add(new UserDTO(u));
        }
        return dto;
    }

    /**
     * Attempt to create a user.
     * @param params The request body parameters.
     * @return The status of creation, containing all possible errors. Errors include invalid password, invalid email or
     * username already taken It also includes the UserDTO. UserDTO will be null if there is an error.
     */
    public CreateUserStatus createUser(CreateUserRequestBody params){
        boolean unameError = !userRepository.findUserByUsername(params.getUsername()).isEmpty();
        boolean emailError = !userRepository.findUserByEmail(params.getEmail()).isEmpty(); //TODO email validation
        boolean passwordError = false; //TODO password validation
        if(unameError | emailError | passwordError) return new CreateUserStatus(unameError, emailError, passwordError, null);
        User u = new User(params.getDisplayname(), params.getEmail(), params.getUsername(), params.getPassword());
        userRepository.save(u);
        return new CreateUserStatus(unameError, emailError, passwordError, new UserDTO(u));
    }

    /**
     * Update a user's display name. This is the only thing a user may update.
     * @param userId The user ID.
     * @param displayName The new display name.
     */
    public void updateUser(Long userId, String displayName){
        User u = userRepository.getById(userId);
        u.setDisplayName(displayName);
        userRepository.save(u);
    }

    /**
     * Update a user's display name. This is the only thing a user may update.
     * @param udrb UpdateDispnameRequestBody containing user ID and new displayname
     * @return UserDTO of updated user
     */
    public UserDTO updateDispname(UpdateDispnameRequestBody udrb){
        User u = userRepository.getById(udrb.getUserid());
        u.setDisplayName(udrb.getDispname());
        userRepository.save(u);
        return new UserDTO(u);
    }
}

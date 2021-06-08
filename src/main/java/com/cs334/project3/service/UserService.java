package com.cs334.project3.service;

import com.cs334.project3.dto.*;
import com.cs334.project3.exceptions.ResourceNotFoundException;
import com.cs334.project3.repo.UserRepository;
import com.cs334.project3.model.User;
import com.cs334.project3.requestbody.CreateUserRequestBody;
import com.cs334.project3.requestbody.UpdateDispnameRequestBody;
import com.cs334.project3.requestbody.UsernamePassword;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public UserDTO getUserById(Long userId) {
        User user = userRepository.getById(userId);
        if (user != null) {
            return new UserDTO(user);
        } else {
            throw new NullPointerException("No such user exists");
        }
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
        boolean emailError = !userRepository.findUserByEmail(params.getEmail()).isEmpty() &&
                params.getEmail().matches("/^(\\w|[-.])+@\\w+\\.\\w+$/gm");
        boolean passwordError = params.getPassword().matches("\"^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(.*){6,}$\"gm");

        if(unameError || emailError || passwordError) return new CreateUserStatus(unameError, emailError, passwordError, null);
        String passwordHash = passwordEncoder.encode(params.getPassword());
        User u = new User(params.getDisplayname(), params.getEmail(), params.getUsername(), passwordHash);
        u.setAvatar_path(params.getAvatar());
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

    public void loginUser(String uname, String jwt){
        User u = userRepository.findUserByUsername(uname).get(0);
        u.setJwt_token(jwt);
        userRepository.save(u);
    }

    public void logoutUser(Long uid){
        try{
            User u = userRepository.getById(uid);
            u.setJwt_token(null);
            userRepository.save(u);
        } catch(Exception e){
            throw new NullPointerException();
        }
    }

    public String getJWT(String username){
        User u = userRepository.findUserByUsername(username).get(0);
        return u.getJwt_token();
    }

    public List<UserDTO> getByUsername(String username) {
        List<User> uList = userRepository.findUserByUsername(username);
        if(uList.size() != 0){
            List<UserDTO> uDTOList = new ArrayList<>();
            for (User u : uList){
                uDTOList.add(new UserDTO(u));
                return uDTOList;
            }
        } else {
            throw new ResourceNotFoundException("No user with username "+username+" exists");
        }
        return new ArrayList<>();
    }

    public UsernamePassword loadUserByUsername(String username){
        List<User> uList = userRepository.findUserByUsername(username);
        if(uList.size() == 0){
            throw new ResourceNotFoundException();
        } else {
            // TODO: Change password hash to password
            UsernamePassword up = new UsernamePassword(uList.get(0).getUsername(), uList.get(0).getPasswordHash());
            return up;
        }
    }
}

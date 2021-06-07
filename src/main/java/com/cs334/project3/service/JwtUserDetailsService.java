package com.cs334.project3.service;

import java.util.ArrayList;

import com.cs334.project3.requestbody.UsernamePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            UsernamePassword up = userService.loadUserByUsername(username);
            // NOT THE SAME AS THE USER MODEL CLASS !!
            return new User(up.getUsername(), up.getPassword(), new ArrayList<>());
        } catch (Exception e){
            throw new UsernameNotFoundException("User not found with username: "+username);
        }
    }
}

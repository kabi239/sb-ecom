package com.ecommerce.sb_ecom.util;

import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    
    @Autowired
    UserRepository userRepository;

    // method to get emailId of logged in user
    public String loggedInEmail() {
        //getting instance of Authentication -> this object will have the details of the user who is currently authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));
        return user.getEmail();
    }

    public Long loggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));
        return user.getUserId();
    }

    public User loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));
                return user;
    }
}
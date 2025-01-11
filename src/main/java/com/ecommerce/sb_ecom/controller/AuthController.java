package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.security.jwt.JwtUtils;
import com.ecommerce.sb_ecom.security.request.LoginRequest;
import com.ecommerce.sb_ecom.security.response.UserInfoResponse;
import com.ecommerce.sb_ecom.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){ // request body should be in LoginRequest format
        //The <?> means it can return any type of response body.

        Authentication authentication;
        // creating spring security authentication object
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            //UsernamePasswordAuthenticationToken is like a "package" that holds the username and password entered by the user during login
            //authenticationManager.authenticate(...): Uses the AuthenticationManager to verify the credentials in the loginRequest.
            //  if the credentials are correct, object "authentication" will contain the authenticated user info.

        }catch (AuthenticationException e){ //If authentication fails, an AuthenticationException is thrown.
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Stores security-related information for the current thread.
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // Retrieves the main user information (e.g., username). and then casting it into UserDetails which represents user-related data.
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        // generating jwtToken using method present in JwtUtils class
        List<String> roles = userDetails.getAuthorities().stream() //Retrieves the user's roles or authorities and convert it into stream
                .map(item ->item.getAuthority()) //Extracts the name of each authority.
                .toList(); // then converting the stream into list
        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),userDetails.getUsername(),roles,jwtToken);


        return ResponseEntity.ok(response);
    }
}

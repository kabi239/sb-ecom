package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.model.AppRole;
import com.ecommerce.sb_ecom.model.Role;
import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.repository.RoleRepository;
import com.ecommerce.sb_ecom.repository.UserRepository;
import com.ecommerce.sb_ecom.security.jwt.JwtUtils;
import com.ecommerce.sb_ecom.security.request.LoginRequest;
import com.ecommerce.sb_ecom.security.request.SignupRequest;
import com.ecommerce.sb_ecom.security.response.MessageResponse;
import com.ecommerce.sb_ecom.security.response.UserInfoResponse;
import com.ecommerce.sb_ecom.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@Valid @RequestBody SignupRequest signupRequest){
        // checking if user exists or not
        if (userRepository.existsByUsername(signupRequest.getUsername())){
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already exists"));
        }
        //checking if email exists
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already exists"));
        }
        // creating new user
        User user = new User(signupRequest.getUsername(),signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole(); // will store role name provided  at time of registration request
        // above one is holding roles in string format cause we are retrieving it from request
        // like from request we are getting - "admin"
        Set<Role> roles = new HashSet<>(); // it will hold role entities to be assigned to the new user
        //so we have to map it with ROLE_ADMIN as this is what we are storing in database
        if (strRoles == null){
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error : Role is not found."));
            roles.add(userRole);
        }else {
            //for every role a user have we are mapping the corresponding
            strRoles.forEach(role -> {
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error : Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error : Role is not found."));
                        roles.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(()-> new RuntimeException("Error : Role is not found."));
                        roles.add(userRole);
                }
            });
            user.setRoles(roles);
            userRepository.save(user);
            }
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}

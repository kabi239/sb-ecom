package com.ecommerce.sb_ecom.security.response;

import java.util.List;

// format of response that will be sent back to user with token
public class UserInfoResponse {


    private  Long Id;
    private String jwtToken;
    private String username;
    private List<String> roles;

    public UserInfoResponse(Long Id,String username, List<String> roles, String jwtToken) {
        this.Id = Id;
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}

package com.example.blogpost.requests;

import com.example.blogpost.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserCredentialRequest {

    @JsonProperty("username")
    private String username;
    private String password;

    public User getUserEntity(){
        User user = new User();
        user.setUserName(username.trim());
        user.setPassword(password.trim());


        return user;
    }
}

package com.example.blogpost.service;

import com.example.blogpost.entity.User;
import com.example.blogpost.requests.UserCredentialRequest;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User NewUser);

    User getUserById(Long userId);

    User getUserByUsername(String userName);

    User updateUser(Long userId, User updatedUser);

    void  deleteById(Long userId, Long adminId);

    boolean authenticate(UserCredentialRequest request);

    boolean usernameExists(String userName);
}

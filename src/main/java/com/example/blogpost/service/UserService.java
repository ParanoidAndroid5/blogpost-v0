package com.example.blogpost.service;

import com.example.blogpost.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User NewUser);

    User getUserById(Long userId);

    User updateUser(Long userId, User updatedUser);

    void  deleteById(Long userId);
}

package com.example.blogpost.controller;

import com.example.blogpost.entity.User;
import com.example.blogpost.repository.UserRepository;
import com.example.blogpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin


public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.saveUser(newUser);
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId){
        return userService.getUserById(userId);

    }
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody User newUser){

        return userService.updateUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId, @RequestParam Long adminUserId)
    {
        userService.deleteById(userId, adminUserId);

    }
}

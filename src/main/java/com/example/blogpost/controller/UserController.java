package com.example.blogpost.controller;

import com.example.blogpost.entity.User;
import com.example.blogpost.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.blogpost.requests.UserCredentialRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")


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

    @PostMapping("/createUser")
    public User createUser(@RequestBody UserCredentialRequest newUser){
        return userService.saveUser(newUser.getUserEntity());
    }


    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId){
        return userService.getUserById(userId);

    }
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId,@Valid @RequestBody User newUser){

        return userService.updateUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId, @RequestParam Long adminUserId)
    {
        userService.deleteById(userId, adminUserId);

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredentialRequest loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest);

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}

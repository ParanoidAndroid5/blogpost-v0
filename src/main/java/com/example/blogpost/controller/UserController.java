package com.example.blogpost.controller;

import com.example.blogpost.entity.User;
import com.example.blogpost.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.blogpost.requests.UserCredentialRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserCredentialRequest loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest);

        Map<String, String> response = new HashMap<>();
        if (isAuthenticated) {
            Long userId = userService.getUserByUsername(loginRequest.getUsername()).getId();
            response.put("message", "Login successful");
            response.put("userId", String.valueOf(userId));
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }


}

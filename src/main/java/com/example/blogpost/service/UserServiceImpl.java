package com.example.blogpost.service;

import com.example.blogpost.entity.User;
import com.example.blogpost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User saveUser(User NewUser){
        return userRepository.save(NewUser);
    }
    public User getUserById(Long userId)
    {
        return userRepository.findById(userId).orElse(null);
    }
    public User updateUser(Long userId, User newUser)
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User updatedUser = user.get();
            updatedUser.setUserName(newUser.getUserName());
            updatedUser.setPassword(newUser.getPassword());
            userRepository.save(updatedUser);
            return updatedUser;
        }else return null;
    }
    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }
}

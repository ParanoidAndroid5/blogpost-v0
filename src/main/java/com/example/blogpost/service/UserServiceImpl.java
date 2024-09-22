package com.example.blogpost.service;

import com.example.blogpost.entity.User;
import com.example.blogpost.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.blogpost.constants.BlogpostConstants.ADMIN_ID;

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

    @Override
    public User getUserByUsername(String userName) {

       return userRepository.findByUserName(userName)
               .orElseThrow(()-> new EntityNotFoundException("user not found."));

    }

    public User updateUser(Long userId, User newUser)
    {
        Optional<User> user = userRepository.findById(userId);
        if (!userId.equals(newUser.getId()) && !ADMIN_ID.equals(userId)){
            throw new RuntimeException("NOT AUTHORIZED");
        }

        if(user.isPresent()){
            User updatedUser = user.get();
            updatedUser.setUserName(newUser.getUserName());
            updatedUser.setPassword(newUser.getPassword());
            userRepository.save(updatedUser);
            return updatedUser;
        }else return null;
    }
    public void deleteById(Long userId, Long adminId){

        if (!ADMIN_ID.equals(adminId)){
            throw new RuntimeException("NOT AUTHORIZED");
        }
        userRepository.deleteById(userId);
    }


    // Authenticate method
    public boolean authenticate(String userName, String password) {
        User user = getUserByUsername(userName);
        return user.getPassword().equals(password);
    }

}

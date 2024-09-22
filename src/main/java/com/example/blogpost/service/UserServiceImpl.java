package com.example.blogpost.service;

import com.example.blogpost.entity.User;
import com.example.blogpost.repository.UserRepository;
import com.example.blogpost.requests.UserCredentialRequest;
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
            updatedUser.setUserName(newUser.getUserName().trim());
            updatedUser.setPassword(newUser.getPassword().trim());
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
    public boolean authenticate(UserCredentialRequest request) {
        User user = getUserByUsername(request.getUserEntity().getUserName());
        return user.getPassword().equals(request.getUserEntity().getPassword());
    }

}

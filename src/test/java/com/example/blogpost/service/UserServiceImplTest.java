package com.example.blogpost.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.blogpost.entity.User;
import com.example.blogpost.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User mockUser;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
    }

    @Test
    void testAuthenticateValidUser() {
        String userName = "testUser";
        String password = "password123";

        mockUser.setUserName(userName);
        mockUser.setPassword(password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(mockUser));

        boolean result = userService.authenticate(userName, password);

        assertTrue(result, "The user should be authenticated with valid credentials.");
    }

    @Test
    void testAuthenticateInvalidPassword() {
        // Arrange
        String userName = "testUser";
        String password = "password123";
        String invalidPassword = "wrongPassword";
        mockUser.setUserName(userName);
        mockUser.setPassword(password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(mockUser));

        boolean result = userService.authenticate(userName, invalidPassword);

        assertFalse(result, "The user should not be authenticated with an invalid password.");
    }

    @Test
    void testAuthenticateUserNotFound() {
        // Arrange
        String userName = "nonExistentUser";
        when(userRepository.findByUserName(userName)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> userService.authenticate(userName, "password"),
                "The authenticate method should throw a NullPointerException when the user is not found.");
    }
}

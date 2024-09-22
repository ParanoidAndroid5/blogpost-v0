package com.example.blogpost.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.blogpost.entity.User;
import com.example.blogpost.repository.UserRepository;
import com.example.blogpost.requests.UserCredentialRequest;
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
    private UserCredentialRequest mockRequest;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockRequest = new UserCredentialRequest();
    }

    @Test
    void testAuthenticateValidUser() {
        String userName = "testUser";
        String password = "password123";

        mockRequest.setUsername(userName);
        mockRequest.setPassword(password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(mockRequest.getUserEntity()));

        boolean result = userService.authenticate(mockRequest);

        assertTrue(result, "The user should be authenticated with valid credentials.");
    }

    @Test
    void testAuthenticateValidUserWithWhiteSpace() {
        String userName = "   testUser   ";
        String password = "  password123  ";

        mockRequest.setUsername(userName);
        mockRequest.setPassword(password);

        when(userRepository.findByUserName(mockRequest.getUserEntity().getUserName())).thenReturn(Optional.ofNullable(mockRequest.getUserEntity()));

        boolean result = userService.authenticate(mockRequest);

        assertTrue(result, "The user should be authenticated with valid credentials.");
    }

    @Test
    void testAuthenticateInvalidPassword() {
        // Arrange
        String userName = "testUser";
        String password = "password123";
        String invalidPassword = "wrongPassword";
        mockRequest.setUsername(userName);
        mockRequest.setPassword(invalidPassword);

        mockUser = mockRequest.getUserEntity();
        mockUser.setPassword(password);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(mockUser));

        boolean result = userService.authenticate(mockRequest);

        assertFalse(result, "The user should not be authenticated with an invalid password.");
    }

    @Test
    void testAuthenticateUserNotFound() {
        // Arrange
        String userName = "nonExistentUser";
        mockRequest.setUsername(userName);
        mockRequest.setPassword("234");
        when(userRepository.findByUserName(userName)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> userService.authenticate(mockRequest),
                "The authenticate method should throw a NullPointerException when the user is not found.");
    }
}

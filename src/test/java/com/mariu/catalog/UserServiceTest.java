package com.mariu.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when; 

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mariu.catalog.model.User; 
import com.mariu.catalog.repository.UserRepository; 
import com.mariu.catalog.services.UserService;

@ExtendWith(MockitoExtension.class) 
public class UserServiceTest {

    @Mock 
    private UserRepository userRepository;

    @InjectMocks 
    private UserService userService;

    
    @Test
    void findByEmail_UserExists_ReturnsUser() {
        // Arrange (Given)
        String testEmail = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(testEmail);
  
        when(userRepository.findByEmail(testEmail)).thenReturn(mockUser);

        // Act (When)
        User foundUser = userService.findByEmail(testEmail);

        // Assert (Then)
        assertNotNull(foundUser, "The found user should not be null");
        assertEquals(testEmail, foundUser.getEmail(), "The email of the found user should match the test email");
        
    }

   
    @Test
    void existsByEmail_UserExists_ReturnsTrue() {
        // Arrange
        String existentEmail = "existing@example.com";
        // Configure mock to return true when user exists
        when(userRepository.existsByEmail(existentEmail)).thenReturn(true);

        // Act
        boolean exists = userService.existsByEmail(existentEmail);

        // Assert
        assertTrue(exists, "User should exist for this email");
    }
}
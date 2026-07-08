package com.microservicio_auth.ms_auth.service;

import com.microservicio_auth.ms_auth.dto.AuthResponse;
import com.microservicio_auth.ms_auth.dto.LoginRequest;
import com.microservicio_auth.ms_auth.model.User;
import com.microservicio_auth.ms_auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService; 

    private User user;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("pablo");
        user.setPassword("encodedPassword");

        loginRequest = new LoginRequest("pablo", "password123");
    }

    @Test
    void login_Success() {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);


        AuthResponse response = authService.login(loginRequest);


        assertNotNull(response);
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void login_InvalidPassword_ThrowsException() {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    }
}

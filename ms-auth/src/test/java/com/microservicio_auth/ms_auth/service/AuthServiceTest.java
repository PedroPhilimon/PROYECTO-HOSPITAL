package com.microservicio_auth.ms_auth.service;

import com.microservicio_auth.ms_auth.dto.AuthResponse;
import com.microservicio_auth.ms_auth.dto.LoginRequest;
import com.microservicio_auth.ms_auth.dto.RegisterRequest;
import com.microservicio_auth.ms_auth.model.Role;
import com.microservicio_auth.ms_auth.model.User;
import com.microservicio_auth.ms_auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "SECRET_KEY", "bXlzZWNyZXRrZXl0aGF0aXNhdGxlYXN0MzJieXRlc2xvbmc=");
        ReflectionTestUtils.setField(authService, "JWT_EXPIRATION", 3600000L); // 1 hora
    }

    @Test
    void register_Success() {
        // CORREGIDO: username, email, password
        RegisterRequest request = new RegisterRequest("juan123", "juan@hospital.com", "passSegura");
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("passwordEncriptada");

        authService.register(request);

        verify(userRepository, times(1)).findByEmail("juan@hospital.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_InvalidEmail_ThrowsException() {
        // CORREGIDO: username, email inválido, password
        RegisterRequest request = new RegisterRequest("juan123", "correo-malo.com", "passSegura");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("El formato del email no es válido o está vacío", exception.getMessage());
        verify(userRepository, never()).save(any(User.class)); // Verificamos que no guarde
    }

    @Test
    void register_EmptyUsername_ThrowsException() {
        // CORREGIDO: username vacío, email, password
        RegisterRequest request = new RegisterRequest("", "juan@hospital.com", "passSegura");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("El nombre de usuario es obligatorio", exception.getMessage());
        verify(userRepository, never()).save(any(User.class)); 
    }

    @Test
    void register_UserAlreadyExists_ThrowsException() {
        // CORREGIDO: username, email, password
        RegisterRequest request = new RegisterRequest("juan123", "juan@hospital.com", "passSegura");
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        LoginRequest request = new LoginRequest("juan@hospital.com", "passSegura");
        
        User user = new User();
        user.setUsername("juan123");
        user.setEmail("juan@hospital.com");
        user.setPassword("passwordEncriptada");
        user.setRole(Role.USER);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        AuthResponse response = authService.login(request);

        assertNotNull(response); 
    }

    @Test
    void login_UserNotFound_ReturnsNull() {
        LoginRequest request = new LoginRequest("noexiste@hospital.com", "passSegura");
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        AuthResponse response = authService.login(request);

        assertNull(response);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_WrongPassword_ReturnsNull() {
        LoginRequest request = new LoginRequest("juan@hospital.com", "passIncorrecta");
        
        User user = new User();
        user.setEmail("juan@hospital.com");
        user.setPassword("passwordEncriptada");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        AuthResponse response = authService.login(request);

        assertNull(response);
    }
}
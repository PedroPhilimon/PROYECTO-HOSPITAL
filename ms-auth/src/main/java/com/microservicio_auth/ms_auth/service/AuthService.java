package com.microservicio_auth.ms_auth.service;

import java.util.Date; // CORREGIDO: Usar java.util.Date en lugar de java.sql.Date
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value; // CORREGIDO: Usar la anotación de Spring
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicio_auth.ms_auth.dto.AuthResponse;
import com.microservicio_auth.ms_auth.dto.LoginRequest;
import com.microservicio_auth.ms_auth.dto.RegisterRequest;
import com.microservicio_auth.ms_auth.model.Role;
import com.microservicio_auth.ms_auth.model.User;
import com.microservicio_auth.ms_auth.repository.UserRepository;

// CORREGIDO: Importaciones de JJWT necesarias para resolver las clases de firmas y tokens
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}") // CORREGIDO: Sintaxis correcta de la propiedad ${...}
    private String SECRET_KEY;

    @Value("${jwt.expiration}") // CORREGIDO: Se añadió el '$' faltante
    private long JWT_EXPIRATION;

    public void register(RegisterRequest request) {
        String email = request.email();
        String username = request.username();

        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            log.warn("Registro fallido - correo inválido {}", email); 
            throw new IllegalArgumentException("El formato del email no es válido o está vacío");
        }

        if (username == null || username.trim().isEmpty()) {
            log.warn("Registro fallido - username vacío");
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }

        Optional<User> existente = userRepository.findByEmail(email);

        if (existente.isPresent()) {
            log.warn("Registro fallido - usuario ya existe {}", email); 
            throw new RuntimeException("El email ya está registrado");
        }

        User nuevoUsuario = new User();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setRole(Role.USER);

        String passwordEncriptada = passwordEncoder.encode(request.password());
        nuevoUsuario.setPassword(passwordEncriptada);
        
        userRepository.save(nuevoUsuario);
        log.info("Usuario {} registrado exitosamente con el correo: {}", username, email);
    }

    //Nombre del método normalizado a camelCase igual que sus llamadas
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(UserDetails usuario) {
        return Jwts.builder()
                .subject(usuario.getUsername())         
                .claim("roles", usuario.getAuthorities()) 
                .issuedAt(new Date())                   
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey())              
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValidToken(String token, UserDetails user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isExpiredToken(token);
    }

    private boolean isExpiredToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }


    public AuthResponse login(LoginRequest request) {
    // 1. Buscar el usuario por email
    var userOpt = userRepository.findByEmail(request.email());
    
    if (userOpt.isEmpty()) {
        log.warn("Login fallido - usuario no existe: {}", request.email());
        return null;
    }

    User user = userOpt.get();

    // 2. Verificar que la contraseña coincida
    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
        log.warn("Login fallido - password incorrecto para: {}", request.email());
        return null;
    }

    // 3. Crear el UserDetails para el generador
    org.springframework.security.core.userdetails.UserDetails userDetails = 
            org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole().name()) 
            .build();

    // 4. Generar el token si todo está OK
    String token = generateToken(userDetails);
    log.info("Login exitoso para: {}", request.email());

    return new AuthResponse(token);
}


}
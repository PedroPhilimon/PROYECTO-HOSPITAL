package com.microservicio_auth.ms_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservicio_auth.ms_auth.service.AuthService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public AuthFilter authFilter(AuthService service, UserDetailsService userDetailsService) {
        return new AuthFilter(service, userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //Se utiliza la clase completa org.springframework.security.core.userdetails.User 
        
        UserDetails user1 = org.springframework.security.core.userdetails.User.withUsername("user")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        UserDetails admin = org.springframework.security.core.userdetails.User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthFilter filter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // CORREGIDO: Apuntar a la ruta real definida en tu controlador (/api/auth/)
                        .requestMatchers("/api/auth/**").permitAll() 
                        .anyRequest().permitAll()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
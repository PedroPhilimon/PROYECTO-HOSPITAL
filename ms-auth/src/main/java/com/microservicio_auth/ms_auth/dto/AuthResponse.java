package com.microservicio_auth.ms_auth.dto;
//El token que le devolveremos al cliente si las credenciales son corrrectas
public record AuthResponse(
    String token
) {}

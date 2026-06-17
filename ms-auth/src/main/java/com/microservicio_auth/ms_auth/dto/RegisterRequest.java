package com.microservicio_auth.ms_auth.dto;

public record RegisterRequest(
    String username,
    String email,
    String password
)
{}

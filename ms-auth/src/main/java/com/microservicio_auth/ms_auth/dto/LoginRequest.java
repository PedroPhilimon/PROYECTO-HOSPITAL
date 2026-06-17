package com.microservicio_auth.ms_auth.dto;

public record LoginRequest(
    String email,
    String password
) {}

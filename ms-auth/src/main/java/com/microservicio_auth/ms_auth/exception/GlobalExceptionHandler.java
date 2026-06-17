package com.microservicio_auth.ms_auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Este método va a capturar CUALQUIER IllegalArgumentException de tu app
    
    

    

    //captura RuntimeException (por ejemplo, para el registro)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // <-- Transforma en un 400 Bad Request
                .body(Map.of("error", ex.getMessage()));
    }
}
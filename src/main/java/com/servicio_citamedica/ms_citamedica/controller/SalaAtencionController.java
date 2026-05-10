package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;
import com.servicio_citamedica.ms_citamedica.repository.SalaAtencionRepository; // Necesitarías crear este repo o usar el service
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaAtencionController {

    private final SalaAtencionRepository salaRepository; 

    @GetMapping
    public List<SalaAtencion> listarSalas() {
        return salaRepository.findAll();
    }

    @PostMapping
    public SalaAtencion crearSala(@RequestBody SalaAtencion sala) {
        return salaRepository.save(sala);
    }
}
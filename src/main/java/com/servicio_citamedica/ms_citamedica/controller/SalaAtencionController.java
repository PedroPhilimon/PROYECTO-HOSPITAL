package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionResponseDTO;
import com.servicio_citamedica.ms_citamedica.service.SalaAtencionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaAtencionController {

    private final SalaAtencionService salaAtencionService;

    @GetMapping
    public ResponseEntity<List<SalaAtencionResponseDTO>> obtenerTodas() {
        List<SalaAtencionResponseDTO> salas = salaAtencionService.findAll();
        return ResponseEntity.ok(salas);
    }

    @PostMapping
    public ResponseEntity<SalaAtencionResponseDTO> crear(@RequestBody SalaAtencionRequestDTO dto) {
        SalaAtencionResponseDTO nuevaSala = salaAtencionService.create(dto);
        return ResponseEntity.ok(nuevaSala);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        salaAtencionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.service.CitaMedicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaMedicaController {

    private final CitaMedicaService citaMedicaService;

    /**
     * Endpoint para obtener todas las citas.
     * GET /api/citas
     */
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> obtenerTodas() {
        List<CitaResponseDTO> citas = citaMedicaService.listarTodas();
        return ResponseEntity.ok(citas);
    }

    /**
     * Endpoint para obtener una cita por su ID.
     * GET /api/citas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaMedicaService.obtenerPorId(id));
    }

    /**
     * Endpoint para crear una nueva cita.
     * POST /api/citas
     */
    @PostMapping
    public ResponseEntity<CitaResponseDTO> crear(@RequestBody CitaRequestDTO request) {
        CitaResponseDTO nuevaCita = citaMedicaService.crearCita(request);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }
}
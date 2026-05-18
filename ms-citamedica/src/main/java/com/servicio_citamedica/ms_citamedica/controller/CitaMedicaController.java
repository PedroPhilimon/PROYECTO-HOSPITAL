package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.service.CitaMedicaService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaMedicaController {

    private final CitaMedicaService citaMedicaService;

    public CitaMedicaController(CitaMedicaService citaMedicaService) {
        this.citaMedicaService = citaMedicaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> findAll() {
        List<CitaResponseDTO> citasMedicas = citaMedicaService.findAll();
        return ResponseEntity.ok(citasMedicas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaMedicaService.findByDto(id));
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> create(@Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO crearCita = citaMedicaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearCita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO actualizarPaciente = citaMedicaService.update(id, dto);
        return ResponseEntity.ok(actualizarPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        citaMedicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
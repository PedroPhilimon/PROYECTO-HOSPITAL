package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.service.CitaMedicaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Slf4j
public class CitaMedicaController {

    private final CitaMedicaService citaMedicaService;

    public CitaMedicaController(CitaMedicaService citaMedicaService) {
        this.citaMedicaService = citaMedicaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> findAll() {
        log.info("Solicitud recibida para obtener todas las citas médicas");
        List<CitaResponseDTO> citasMedicas = citaMedicaService.findAll();
         log.info("Se encontraron {} citas médicas",
                citasMedicas.size());
        return ResponseEntity.ok(citasMedicas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Solicitud recibida para buscar cita médica con ID: {}", id);
        return ResponseEntity.ok(citaMedicaService.findByDto(id));
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> create(@Valid @RequestBody CitaRequestDTO dto) {
        log.info("Solicitud recibida para crear una nueva cita médica");
        CitaResponseDTO crearCita = citaMedicaService.create(dto);
        log.info("Cita médica creada exitosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(crearCita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CitaRequestDTO dto) {
        log.info("Solicitud recibida para actualizar la cita médica con ID: {}", id);
        CitaResponseDTO actualizarPaciente = citaMedicaService.update(id, dto);
        log.info("Cita médica actualizada exitosamente con ID: {}", id);
        return ResponseEntity.ok(actualizarPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Solicitud recibida para eliminar la cita médica con ID: {}", id);
        citaMedicaService.delete(id);
        log.info("Cita médica eliminada exitosamente con ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
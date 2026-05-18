package com.pacientes.servicio_pacientes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.dto.PacienteResponseDTO;
import com.pacientes.servicio_pacientes.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> findAll() {
        List<PacienteResponseDTO> pacientes = pacienteService.findAll();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> findByDto(@PathVariable Long id) {
        PacienteResponseDTO buscarPacienteId = pacienteService.findByDto(id);
        return ResponseEntity.ok(buscarPacienteId);
    }

     @PostMapping
    public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO crearPaciente = pacienteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO actualizarPaciente = pacienteService.update(id, dto);
        return ResponseEntity.ok(actualizarPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.pacientes.servicio_pacientes.controller;

import com.pacientes.servicio_pacientes.dto.HistorialRequestDTO;
import com.pacientes.servicio_pacientes.dto.HistorialResponseDTO;
import com.pacientes.servicio_pacientes.service.HistorialPacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@RequiredArgsConstructor
public class HistorialPacienteController {
    
    private final HistorialPacienteService historialService;

    // Crear un historial para un paciente específico
    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistorialResponseDTO> create(@PathVariable Long pacienteId, @RequestBody HistorialRequestDTO dto) {
        return new ResponseEntity<>(historialService.create(pacienteId, dto), HttpStatus.CREATED);
    }

    // Obtener todos los historiales de un paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialResponseDTO>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historialService.findByPacienteId(pacienteId));
    }

    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
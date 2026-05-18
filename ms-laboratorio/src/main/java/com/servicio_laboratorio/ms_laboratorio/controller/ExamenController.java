package com.servicio_laboratorio.ms_laboratorio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_laboratorio.ms_laboratorio.dto.ExamenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.ExamenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.service.ExamenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/examenes")
public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    @GetMapping
    public ResponseEntity<List<ExamenResponseDTO>> findAll() {
        List<ExamenResponseDTO> examenesMedicos = examenService.obtenerTodosLosExamenes();
        return ResponseEntity.ok(examenesMedicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(examenService.obtenerExamenPorId(id));
    }

    @PostMapping
    public ResponseEntity<ExamenResponseDTO> create(@Valid @RequestBody ExamenRequestDTO dto) {
        ExamenResponseDTO crearExamen = examenService.crearExamen(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearExamen);
    }
}

package com.servicio_agenda.ms_agenda.controller;

import com.servicio_agenda.ms_agenda.dto.AsignacionSalaRequestDTO;
import com.servicio_agenda.ms_agenda.dto.AsignacionSalaResponseDTO;
import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.service.AsignacionSalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones-salas")
public class AsignacionSalaController {

    private final AsignacionSalaService asignacionSalaService;

    public AsignacionSalaController(AsignacionSalaService asignacionSalaService) {
        this.asignacionSalaService = asignacionSalaService;
    }

    @GetMapping
    public ResponseEntity<List<AsignacionSala>> listarTodas() {
        List<AsignacionSala> asignaciones = asignacionSalaService.listarTodas();
        return ResponseEntity.ok(asignaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AsignacionSala>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asignacionSalaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AsignacionSalaResponseDTO> crearAsignacionSala(@Valid @RequestBody AsignacionSalaRequestDTO dto) {
        AsignacionSalaResponseDTO crearAsignacionSala = asignacionSalaService.guardar(dto, dto.getIdSala());
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAsignacionSala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asignacionSalaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
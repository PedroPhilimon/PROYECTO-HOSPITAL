package com.servicio_historial.ms_historial.controller;

import com.servicio_historial.ms_historial.dto.RegistroClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.RegistroClinicoResponseDTO;
import com.servicio_historial.ms_historial.service.RegistroClinicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor 
public class RegistroClinicoController {

    private final RegistroClinicoService service;

    @GetMapping
    public List<RegistroClinicoResponseDTO> obtenerTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroClinicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RegistroClinicoResponseDTO> crear(@Valid @RequestBody RegistroClinicoRequestDTO dto) {
        RegistroClinicoResponseDTO creado = service.guardar(dto);
        return ResponseEntity.ok(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
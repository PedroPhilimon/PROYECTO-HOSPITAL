package com.servicio_inventario.ms_inventario.controller;

import com.servicio_inventario.ms_inventario.dto.MovimientoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.MovimientoResponseDTO;
import com.servicio_inventario.ms_inventario.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoResponseDTO>> getAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findByDto(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> create(@Valid @RequestBody MovimientoRequestDTO dto) {
        MovimientoResponseDTO nuevoMovimiento = movimientoService.save(dto);
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MovimientoRequestDTO dto) {
        MovimientoResponseDTO actualizarMovimiento = movimientoService.update(id, dto);
        return ResponseEntity.ok(actualizarMovimiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
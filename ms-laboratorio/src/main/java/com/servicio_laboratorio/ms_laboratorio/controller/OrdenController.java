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

import com.servicio_laboratorio.ms_laboratorio.dto.OrdenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.OrdenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.service.OrdenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> findAll() {
        List<OrdenResponseDTO> citasMedicas = ordenService.obtenerTodasLasOrdenes();
        return ResponseEntity.ok(citasMedicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.obtenerOrdenPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrdenResponseDTO> create(@Valid @RequestBody OrdenRequestDTO dto) {
        OrdenResponseDTO crearOrden = ordenService.crearOrden(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearOrden);
    }

}

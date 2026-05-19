package com.servicio_proveedores.ms_proveedores.controller;

import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import com.servicio_proveedores.ms_proveedores.service.OrdenCompraService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-compra")
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;

    public OrdenCompraController(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenCompraResponseDTO>> findAll() {
        List<OrdenCompraResponseDTO> ordenes = ordenCompraService.listarTodas();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenCompraService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrdenCompraResponseDTO> create(@Valid @RequestBody OrdenCompraRequestDTO dto) {
        OrdenCompraResponseDTO crearOrden = ordenCompraService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearOrden);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> update(@PathVariable Long id, @Valid @RequestBody OrdenCompraRequestDTO dto) {
        OrdenCompraResponseDTO actualizarOrden = ordenCompraService.actualizar(id, dto);
        return ResponseEntity.ok(actualizarOrden);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ordenCompraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
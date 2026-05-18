package com.servicio_facturacion.ms_facturacion.controller;

import com.servicio_facturacion.ms_facturacion.dto.FacturaRequestDTO;
import com.servicio_facturacion.ms_facturacion.dto.FacturaResponseDTO;
import com.servicio_facturacion.ms_facturacion.dto.DetalleResponseDTO;
import com.servicio_facturacion.ms_facturacion.model.DetalleFactura;
import com.servicio_facturacion.ms_facturacion.service.FacturaService;
import com.servicio_facturacion.ms_facturacion.service.DetalleFacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;
    private final DetalleFacturaService detalleFacturaService; // Inyectas ambos servicios

    @PostMapping
    public ResponseEntity<FacturaResponseDTO> crearFactura(@Valid @RequestBody FacturaRequestDTO dto) {
        return new ResponseEntity<>(facturaService.crearFactura(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<FacturaResponseDTO>> obtenerTodasLasFacturas() {
        return ResponseEntity.ok(facturaService.obtenerTodasLasFacturas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    //DetalleFactura

    @PostMapping("/{facturaId}/detalles")
    public ResponseEntity<Void> agregarDetalle(
            @PathVariable Long facturaId,
            @Valid @RequestBody DetalleFactura detalleFactura) {
        
        detalleFacturaService.agregarDetalle(facturaId, detalleFactura);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{facturaId}/detalles")
    public ResponseEntity<List<DetalleResponseDTO>> obtenerDetallesPorFactura(@PathVariable Long facturaId) {
        return ResponseEntity.ok(detalleFacturaService.obtenerDetallesPorFactura(facturaId));
    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<DetalleResponseDTO> obtenerDetallePorId(@PathVariable Long id) {
        return ResponseEntity.ok(detalleFacturaService.obtenerDetallePorId(id));
    }

    @DeleteMapping("/detalles/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        detalleFacturaService.eliminarDetalle(id);
        return ResponseEntity.noContent().build();
    }
}
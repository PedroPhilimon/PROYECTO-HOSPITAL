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

}
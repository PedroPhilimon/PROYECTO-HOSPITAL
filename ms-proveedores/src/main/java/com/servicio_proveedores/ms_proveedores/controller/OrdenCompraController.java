package com.servicio_proveedores.ms_proveedores.controller;

import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.service.impl.OrdenCompraServiceImpl;
import com.servicio_proveedores.ms_proveedores.service.impl.ProveedorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenes-compra")
@RequiredArgsConstructor
public class OrdenCompraController {

    private final OrdenCompraServiceImpl ordenCompraService;
    private final ProveedorServiceImpl proveedorService;

    @GetMapping
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerTodas() {
        List<OrdenCompraResponseDTO> ordenes = ordenCompraService.listarTodas().stream()
                .map(OrdenCompraResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ordenCompraService.buscarPorId(id)
                .map(OrdenCompraResponseDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody OrdenCompraRequestDTO dto) {
        if (dto.getIdProveedor() == null || dto.getIdProveedor().getId() == null) {
            return ResponseEntity.badRequest().body("Debe proporcionar un ID de proveedor vÃ¡lido.");
        }

        // Buscar el proveedor real para asociarlo a la orden de compra
        Optional<Proveedor> proveedorOpt = proveedorService.buscarPorId(dto.getIdProveedor().getId());
        if (proveedorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El proveedor con ID " + dto.getIdProveedor().getId() + " no existe.");
        }

        try {
            OrdenCompra orden = new OrdenCompra();
            orden.setIdItemInventario(dto.getIdItemInventario());
            orden.setCantidadPedida(dto.getCantidadPedida());
            orden.setFechaPedido(dto.getFechaPedido());
            orden.setMontoEstimado(dto.getMontoEstimado());
            orden.setEstado(dto.getEstado());
            orden.setProveedor(proveedorOpt.get());

            // Guarda y ejecuta las validaciones de stock con ms-inventario y alertas a ms-factura
            OrdenCompra guardada = ordenCompraService.guardar(orden);
            return ResponseEntity.status(HttpStatus.CREATED).body(OrdenCompraResponseDTO.fromEntity(guardada));
            
        } catch (RuntimeException e) {
            // Captura errores cuando ms-inventario no tiene stock o rechaza la solicitud
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return ordenCompraService.buscarPorId(id)
                .map(o -> {
                    ordenCompraService.eliminar(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
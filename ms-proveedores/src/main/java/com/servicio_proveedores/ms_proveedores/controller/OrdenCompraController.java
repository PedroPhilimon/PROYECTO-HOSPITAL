package com.servicio_proveedores.ms_proveedores.controller;

import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import com.servicio_proveedores.ms_proveedores.service.OrdenCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-compra")
@Slf4j
@Tag(name = "Órdenes de Compra", description = "Operaciones relacionadas con las órdenes de compra de insumos")
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;

    public OrdenCompraController(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las órdenes de compra", description = "Obtiene una lista de todas las órdenes de compra")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Órdenes de compra encontradas exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenCompraResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de órdenes de compra")
            }
    )
    public ResponseEntity<List<OrdenCompraResponseDTO>> findAll() {
        log.info("Solicitud para obtener todas las órdenes de compra");
        List<OrdenCompraResponseDTO> ordenes = ordenCompraService.listarTodas();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una orden de compra específica", description = "Obtiene una orden de compra específica según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Orden de compra encontrada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenCompraResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada en la base de datos:")
            }
    )
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Solicitud para obtener la orden de compra con ID: {}", id);
        return ResponseEntity.ok(ordenCompraService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea una orden de compra", description = "Crea una orden de compra con todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Orden de compra creada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenCompraResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear orden de compra")
            }
    )
    public ResponseEntity<OrdenCompraResponseDTO> create(@Valid @RequestBody OrdenCompraRequestDTO dto) {
        log.info("Solicitud para crear una nueva orden de compra para el proveedor ID: {}", dto.getIdProveedor());
        OrdenCompraResponseDTO crearOrden = ordenCompraService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearOrden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una orden de compra", description = "Actualiza la orden de compra y la guarda en la db")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Orden de compra actualizada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenCompraResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada D:")
            }
    )
    public ResponseEntity<OrdenCompraResponseDTO> update(@PathVariable Long id, @Valid @RequestBody OrdenCompraRequestDTO dto) {
        log.info("Solicitud para actualizar la orden de compra con ID: {}", id);
        OrdenCompraResponseDTO actualizarOrden = ordenCompraService.actualizar(id, dto);
        return ResponseEntity.ok(actualizarOrden);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una orden de compra específica", description = "Elimina una orden de compra según su id")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Orden de compra eliminada exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: La orden de compra no fue encontrada con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Solicitud para eliminar la orden de compra con ID: {}", id);
        ordenCompraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
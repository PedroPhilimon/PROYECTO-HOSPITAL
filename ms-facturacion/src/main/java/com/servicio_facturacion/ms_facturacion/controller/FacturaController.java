package com.servicio_facturacion.ms_facturacion.controller;

import com.servicio_facturacion.ms_facturacion.dto.FacturaRequestDTO;
import com.servicio_facturacion.ms_facturacion.dto.FacturaResponseDTO;
import com.servicio_facturacion.ms_facturacion.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con las facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    @Operation(summary = "Crea una factura", description = "Crea una factura con todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Factura creada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear factura")
            }
    )
    public ResponseEntity<FacturaResponseDTO> crearFactura(@Valid @RequestBody FacturaRequestDTO dto) {
        return new ResponseEntity<>(facturaService.crearFactura(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una factura específica", description = "Obtiene una factura específica según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Factura encontrada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Factura no encontrada en la base de datos:")
            }
    )
    public ResponseEntity<FacturaResponseDTO> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las facturas", description = "Obtiene una lista de todas las facturas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Facturas encontradas exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de facturas")
            }
    )
    public ResponseEntity<List<FacturaResponseDTO>> obtenerTodasLasFacturas() {
        return ResponseEntity.ok(facturaService.obtenerTodasLasFacturas());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una factura específica", description = "Elimina una factura según su id")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Factura eliminada exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: La factura no fue encontrada con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
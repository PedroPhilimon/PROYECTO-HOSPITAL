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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ordenes")
@Tag(name = "Órdenes de Laboratorio", description = "Operaciones relacionadas con las órdenes de exámenes médicos de laboratorio")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las órdenes", description = "Obtiene una lista de todas las órdenes de laboratorio")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Órdenes encontradas exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de órdenes")
            }
    )
    public ResponseEntity<List<OrdenResponseDTO>> findAll() {
        List<OrdenResponseDTO> citasMedicas = ordenService.obtenerTodasLasOrdenes();
        return ResponseEntity.ok(citasMedicas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una orden específica", description = "Obtiene una orden de laboratorio específica según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Orden encontrada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Orden no encontrada en la base de datos:")
            }
    )
    public ResponseEntity<OrdenResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.obtenerOrdenPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea una orden", description = "Crea una orden de laboratorio con todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Orden creada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdenResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear orden")
            }
    )
    public ResponseEntity<OrdenResponseDTO> create(@Valid @RequestBody OrdenRequestDTO dto) {
        OrdenResponseDTO crearOrden = ordenService.crearOrden(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearOrden);
    }

}
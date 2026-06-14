package com.servicio_historial.ms_historial.controller;

import com.servicio_historial.ms_historial.dto.HistorialClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.HistorialClinicoResponseDTO;
import com.servicio_historial.ms_historial.service.HistorialClinicoService;
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
@RequestMapping("/api/historiales")
@Tag(name = "Historiales Clínicos", description = "Operaciones relacionadas con los historiales clínicos de los pacientes")
@RequiredArgsConstructor
public class HistorialClinicoController {

    private final HistorialClinicoService service;

    @GetMapping
    @Operation(summary = "Obtiene todos los historiales clínicos", description = "Obtiene una lista de todos los historiales clínicos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Historiales clínicos encontrados exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialClinicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de historiales clínicos")
            }
    )
    public ResponseEntity<List<HistorialClinicoResponseDTO>> obtenerTodos() {
        List<HistorialClinicoResponseDTO> historiales = service.listarTodos();
        return ResponseEntity.ok(historiales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un historial clínico específico", description = "Obtiene un historial clínico específico según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Historial clínico encontrado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialClinicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Historial clínico no encontrado en la base de datos:")
            }
    )
    public ResponseEntity<HistorialClinicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crea un historial clínico", description = "Crea un historial clínico con todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Historial clínico creado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialClinicoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear historial clínico")
            }
    )
    public ResponseEntity<HistorialClinicoResponseDTO> crear(@Valid @RequestBody HistorialClinicoRequestDTO dto) {
        HistorialClinicoResponseDTO creado = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un historial clínico específico", description = "Elimina un historial clínico según su id")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Historial clínico eliminado exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: El historial clínico no fue encontrado con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
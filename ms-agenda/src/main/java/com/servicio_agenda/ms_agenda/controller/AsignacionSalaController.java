package com.servicio_agenda.ms_agenda.controller;

import com.servicio_agenda.ms_agenda.dto.AsignacionSalaRequestDTO;
import com.servicio_agenda.ms_agenda.dto.AsignacionSalaResponseDTO;
import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.service.AsignacionSalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones-salas")
@Tag(name = "Asignaciones salas", description = "Operaciones relacionadas con las asignaciones a salas")
public class AsignacionSalaController {

    private final AsignacionSalaService asignacionSalaService;

    public AsignacionSalaController(AsignacionSalaService asignacionSalaService) {
        this.asignacionSalaService = asignacionSalaService;
    }


    @Operation(summary = "Obtiene todos las salas existentes", description = "Obtiene una lista de todas las salas existentes")
    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "salas encontradas exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsignacionSalaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de salas")
            }
    )
    public ResponseEntity<List<AsignacionSala>> listarTodas() {
        List<AsignacionSala> asignaciones = asignacionSalaService.listarTodas();
        return ResponseEntity.ok(asignaciones);
    }


    @Operation(summary = "Obtiene una asignación específica", description = "Obtiene la información de una sala y su asignación existente")
    @GetMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sala encontrada exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsignacionSalaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de la sala")
            }
    )
    public ResponseEntity<Optional<AsignacionSala>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asignacionSalaService.buscarPorId(id));
    }


    @Operation(summary = "Crea una asignación de sala", description = "Crea una asignación con todos sus detalles")
    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Asignación dada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsignacionSalaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear asignación")
            }
    )
    public ResponseEntity<AsignacionSalaResponseDTO> crearAsignacionSala(@Valid @RequestBody AsignacionSalaRequestDTO dto) {
        AsignacionSalaResponseDTO crearAsignacionSala = asignacionSalaService.guardar(dto, dto.getIdSala());
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAsignacionSala);
    }


    @Operation(summary = "Elimina una asignación existente", description = "Elimina una asignación según su id")
    @DeleteMapping("/{id}")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Asignación de sala eliminada exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: La asignación no fue encontrada con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asignacionSalaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
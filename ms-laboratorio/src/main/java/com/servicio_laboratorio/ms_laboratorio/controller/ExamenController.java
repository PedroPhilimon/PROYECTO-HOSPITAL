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

import com.servicio_laboratorio.ms_laboratorio.dto.ExamenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.ExamenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.service.ExamenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/examenes")
@Tag(name = "Exámenes", description = "Operaciones relacionadas con el catálogo de exámenes médicos de laboratorio")
public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    @GetMapping
    @Operation(summary = "Obtiene todos los exámenes", description = "Obtiene una lista de todos los exámenes médicos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exámenes encontrados exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de exámenes")
            }
    )
    public ResponseEntity<List<ExamenResponseDTO>> findAll() {
        List<ExamenResponseDTO> examenesMedicos = examenService.obtenerTodosLosExamenes();
        return ResponseEntity.ok(examenesMedicos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un examen específico", description = "Obtiene un examen específico según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Examen encontrado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Examen no encontrado en la base de datos:")
            }
    )
    public ResponseEntity<ExamenResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(examenService.obtenerExamenPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea un examen", description = "Crea un examen médico con todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Examen creado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear examen")
            }
    )
    public ResponseEntity<ExamenResponseDTO> create(@Valid @RequestBody ExamenRequestDTO dto) {
        ExamenResponseDTO crearExamen = examenService.crearExamen(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearExamen);
    }
}
package com.pacientes.servicio_pacientes.controller;

import java.util.List;

import com.pacientes.servicio_pacientes.model.Paciente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.dto.PacienteResponseDTO;
import com.pacientes.servicio_pacientes.service.PacienteService;

import jakarta.validation.Valid;

import javax.print.attribute.standard.Media;


@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Operaciones relacionadas con los pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @GetMapping
    @Operation(summary = "Obtiene todos los pacientes", description = "Obtiene una lista de todos los pacientes")
    public ResponseEntity<List<PacienteResponseDTO>> findAll() {
        List<PacienteResponseDTO> pacientes = pacienteService.findAll();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> findByDto(@PathVariable Long id) {
        PacienteResponseDTO buscarPacienteId = pacienteService.findByDto(id);
        return ResponseEntity.ok(buscarPacienteId);
    }

     @PostMapping
    public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO crearPaciente = pacienteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPaciente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un paciente", description = "Actualiza el paciente y lo guarda en la db")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente!",
                        content = @Content(mediaType = "Aplication/json",
                            schema = @Schema(implementation = Paciente.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente no encontrado D:")
            }
    )
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO actualizarPaciente = pacienteService.update(id, dto);
        return ResponseEntity.ok(actualizarPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
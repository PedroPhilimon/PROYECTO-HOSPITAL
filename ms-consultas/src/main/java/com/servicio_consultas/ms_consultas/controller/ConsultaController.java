package com.servicio_consultas.ms_consultas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_consultas.ms_consultas.dto.ConsultaRequestDTO;
import com.servicio_consultas.ms_consultas.dto.ConsultaResponseDTO;
import com.servicio_consultas.ms_consultas.model.Consulta;
import com.servicio_consultas.ms_consultas.service.ConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/consultas")
@Tag(name = "Consultas", description = "Operaciones relacionadas con las consultas médicas")
@RequiredArgsConstructor 
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    @Operation(summary = "Crea una consulta", description = "Crea una consulta con todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Consulta creada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear consulta")
            }
    )
    public ResponseEntity<ConsultaResponseDTO> registrarConsulta(@Valid @RequestBody ConsultaRequestDTO dto) {
        Consulta nuevaConsulta = consultaService.registrarConsulta(dto);
        
        ConsultaResponseDTO response = ConsultaResponseDTO.builder()
                .id(nuevaConsulta.getId())
                .pacienteId(nuevaConsulta.getPacienteId())
                .medicoId(nuevaConsulta.getMedicoId())
                .citaId(nuevaConsulta.getCitaId())
                .fecha(nuevaConsulta.getFecha())
                .motivaConsulta(nuevaConsulta.getMotivoConsulta())
                .diagnostico(nuevaConsulta.getDiagnostico())
                .observaciones(nuevaConsulta.getObservaciones())
                .build();
                
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una consulta específica", description = "Obtiene una consulta específica según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Consulta encontrada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Consulta no encontrada en la base de datos:")
            }
    )
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {        
        Consulta consulta = consultaService.buscarPorId(id);
    
        ConsultaResponseDTO response = ConsultaResponseDTO.builder()
            .id(consulta.getId())
            .pacienteId(consulta.getPacienteId())
            .medicoId(consulta.getMedicoId())
            .citaId(consulta.getCitaId())
            .fecha(consulta.getFecha())
            .motivaConsulta(consulta.getMotivoConsulta())
            .diagnostico(consulta.getDiagnostico())
            .observaciones(consulta.getObservaciones())
            .build();
            
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una consulta específica", description = "Elimina una consulta según su id")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Consulta personalizada eliminada exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: La consulta no fue encontrada con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id) {
        consultaService.eliminarConsulta(id);

        return ResponseEntity.noContent().build();
    }
}
package com.pacientes.servicio_pacientes.controller;

import com.pacientes.servicio_pacientes.dto.HistorialRequestDTO;
import com.pacientes.servicio_pacientes.dto.HistorialResponseDTO;
import com.pacientes.servicio_pacientes.service.HistorialPacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@Tag(name = "Historiales", description = "Operaciones relacionadas con el historial de los pacientes")
@RequiredArgsConstructor
public class HistorialPacienteController {
    
    private final HistorialPacienteService historialService;

    @Operation(summary = "Crea un historial para luego asignarlo a un paciente", description = "Luego de crear el historial se le asigna al paciente según su id")
    @PostMapping("/paciente/{pacienteId}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Historial creado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear paciente")
            }
    )
    public ResponseEntity<HistorialResponseDTO> create(@PathVariable Long pacienteId, @RequestBody HistorialRequestDTO dto) {
        return new ResponseEntity<>(historialService.create(pacienteId, dto), HttpStatus.CREATED);
    }


    @Operation(summary = "Obtiene el historial de un paciente segun su id", description = "Obtiene el historial de un paciente específico detalladamente")
    @GetMapping("/paciente/{pacienteId}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Historial de paciente encontrado exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No existe el historial del paciente buscado")
            }
    )
    public ResponseEntity<List<HistorialResponseDTO>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historialService.findByPacienteId(pacienteId));
    }

    @Operation(summary = "Elimina el historial de un paciente segun su id", description = "Elimina el historial de un paciente específico")
    @DeleteMapping("/paciente/{id}")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Historial médico eliminado exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: El historial médico no fue encontrado con el ID proporcionado"
                )
        }
)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
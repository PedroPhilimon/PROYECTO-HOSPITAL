package com.servicio_agenda.ms_agenda.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import com.servicio_agenda.ms_agenda.dto.AgendaMedicoResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.service.AgendaMedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendas")
@Tag(name = "Agenda", description = "Operaciones relacionadas con la agenda del médico")
public class AgendaMedicoController {

    private final AgendaMedicoService  agendaMedicoService;

    public AgendaMedicoController(AgendaMedicoService agendaMedicoService) {
        this.agendaMedicoService = agendaMedicoService;
    }

    
    @Operation(summary = "Obtiene todos las agendas disponibles", description = "Obtiene una lista de todas las agendas disponibles")
    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendas encontradas exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendaMedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de agendas médicas")
            }
    )
    public ResponseEntity<List<AgendaMedico>> listarTodas() {
        List<AgendaMedico> agendas = agendaMedicoService.listarTodas();
        return ResponseEntity.ok(agendas);
    }


    @Operation(summary = "Obtiene una agenda médica específica", description = "Obtiene la información de una agenda médica disponible")
    @GetMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agenda encontrada exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendaMedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de la agenda médica")
            }
    )
    public ResponseEntity<Optional<AgendaMedico>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendaMedicoService.buscarPorId(id));
    }


    @Operation(summary = "Crea una agenda médica", description = "Crea una agenda médica con todos sus detalles")
    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Agenda creada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendaMedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear agenda")
            }
    )
    public ResponseEntity<AgendaMedicoResponseDTO> crearAgenda(@Valid @RequestBody AgendaMedico dto) {
        AgendaMedicoResponseDTO crearAgenda = agendaMedicoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAgenda);
    }


    @Operation(summary = "Elimina una agenda específica", description = "Elimina una agenda según su id")
    @DeleteMapping("/{id}")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Agenda eliminada exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: La agenda no fue encontrada con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agendaMedicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
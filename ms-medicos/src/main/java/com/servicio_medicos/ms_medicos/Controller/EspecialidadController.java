package com.servicio_medicos.ms_medicos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_medicos.ms_medicos.Service.EspecialidadService;
import com.servicio_medicos.ms_medicos.dto.EspecialidadRequestDTO;
import com.servicio_medicos.ms_medicos.dto.EspecialidadResponseDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/especialidades")
@Slf4j
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @Operation(summary = "Obtiene todos las especialidades", description = "Obtiene una lista de todas las especialidades")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Especialidade encontradas exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de especialidades")
            }
    )
    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDTO>> findAll() {
        log.info("Solicitud recibida para obtener todas las especialidades");
        List<EspecialidadResponseDTO> especialidades = especialidadService.findAll();
        return ResponseEntity.ok(especialidades);
    }


    @Operation(summary = "Obtiene un médico es específico", description = "Obtiene una especialidad específica y todos sus detalles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Especialidad encontrado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Error: La especialidad no existe")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> findByDto(@PathVariable Long id) {
        log.info("Solicitud recibida para buscar especialidad con id: {}",id);
        EspecialidadResponseDTO buscarMedicoId = especialidadService.findById(id);
        return ResponseEntity.ok(buscarMedicoId);
    }


   @Operation(summary = "Crea una especialidad", description = "Crea una especialidad con todos sus detalles")
   @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Especialidad creada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EspecialidadResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear la especialidad")
            }
    )
    @PostMapping
    public ResponseEntity<EspecialidadResponseDTO> create(@Valid @RequestBody EspecialidadRequestDTO dto) {
        log.info("Solicitud recibida para crear especialidad: {}",dto);
        EspecialidadResponseDTO crearMedico = especialidadService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearMedico);
    }


}

package com.servicio_medicos.ms_medicos.Controller;

import com.servicio_medicos.ms_medicos.Service.MedicoService;
import com.servicio_medicos.ms_medicos.dto.MedicoRequestDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Controller para la gestión de médicos y sus especialidades")
@Slf4j
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Registrar un nuevo médico", description = "Crea un registro de médico en el sistema junto con sus datos generales")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Médico creado exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> registrarMedico(@Valid @RequestBody MedicoRequestDTO requestDTO) {
        log.info("Registrando nuevo médico: {} {}", requestDTO.getNombre(), requestDTO.getApellido());
        MedicoResponseDTO responseDTO = medicoService.create(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener médico por ID", description = "Busca y retorna el perfil de un médico específico mediante su identificador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Médico encontrado",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Médico no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> obtenerMedicoPorId(@PathVariable Long id) {
        log.info("Consultando médico con ID: {}", id);
        MedicoResponseDTO responseDTO = medicoService.findById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Listar todos los médicos", description = "Retorna una lista completa de todos los médicos registrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de médicos")
            }
    )
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarMedicos() {
        log.info("Consultando todos los médicos");
        List<MedicoResponseDTO> medicos = medicoService.findAll();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar datos del médico", description = "Modifica la información existente de un médico por su ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
                    @ApiResponse(responseCode = "404", description = "Médico no encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> actualizarMedico(@PathVariable Long id, @Valid @RequestBody MedicoRequestDTO requestDTO) {
        log.info("Modificando médico con ID: {} ");
        MedicoResponseDTO responseDTO = medicoService.update(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un médico", description = "Elimina de forma lógica o física el registro de un médico del sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Médico eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Médico no encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        log.info("eliminando médico con ID: {} ");
        medicoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
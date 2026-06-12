package com.servicio_medicos.ms_medicos.Controller;

import com.servicio_medicos.ms_medicos.Service.MedicoService;
import com.servicio_medicos.ms_medicos.dto.MedicoRequestDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Controller para la gestión de médicos y sus especialidades")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo médico", description = "Crea un registro de médico en el sistema junto con sus datos generales")
    @ApiResponse(responseCode = "201", description = "Médico creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
    public ResponseEntity<MedicoResponseDTO> registrarMedico(@RequestBody MedicoRequestDTO requestDTO) {
        MedicoResponseDTO responseDTO = medicoService.registrarMedico(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener médico por ID", description = "Busca y retorna el perfil de un médico específico mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Médico encontrado")
    @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    public ResponseEntity<MedicoResponseDTO> obtenerMedicoPorId(@PathVariable Long id) {
        MedicoResponseDTO responseDTO = medicoService.obtenerMedicoPorId(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar todos los médicos", description = "Retorna una lista completa de todos los médicos registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<MedicoResponseDTO>> listarMedicos() {
        List<MedicoResponseDTO> medicos = medicoService.listarMedicos();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos del médico", description = "Modifica la información existente de un médico por su ID")
    @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    public ResponseEntity<MedicoResponseDTO> actualizarMedico(@PathVariable Long id, @RequestBody MedicoRequestDTO requestDTO) {
        MedicoResponseDTO responseDTO = medicoService.actualizarMedico(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un médico", description = "Elimina de forma lógica o física el registro de un médico del sistema")
    @ApiResponse(responseCode = "204", description = "Médico eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        medicoService.eliminarMedico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
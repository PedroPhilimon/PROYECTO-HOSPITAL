package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.service.CitaMedicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas Médicas", description = "Controller para la programación y gestión de citas de pacientes")
public class CitaMedicaController {

    @Autowired
    private CitaMedicaService citaMedicaService;

    @PostMapping
    @Operation(summary = "Agendar una cita médica", description = "Crea una nueva cita vinculando a un paciente, un médico y un horario")
    @ApiResponse(responseCode = "201", description = "Cita agendada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error en la validación de datos o conflicto de horario")
    public ResponseEntity<CitaResponseDTO> crearCita(@RequestBody CitaRequestDTO requestDTO) {
        CitaResponseDTO responseDTO = citaMedicaService.crearCita(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de una cita", description = "Busca y retorna la información de una cita específica por su ID")
    @ApiResponse(responseCode = "200", description = "Cita encontrada")
    @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    public ResponseEntity<CitaResponseDTO> obtenerCitaPorId(@PathVariable Long id) {
        CitaResponseDTO responseDTO = citaMedicaService.obtenerCitaPorId(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar todas las citas", description = "Retorna el historial de todas las citas médicas registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<CitaResponseDTO>> listarCitas() {
        List<CitaResponseDTO> citas = citaMedicaService.listarCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar una cita médica", description = "Permite reprogramar o actualizar los detalles de una cita existente")
    @ApiResponse(responseCode = "200", description = "Cita modificada exitosamente")
    @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    public ResponseEntity<CitaResponseDTO> actualizarCita(@PathVariable Long id, @RequestBody CitaRequestDTO requestDTO) {
        CitaResponseDTO responseDTO = citaMedicaService.actualizarCita(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar una cita médica", description = "Elimina o cancela el registro de la cita seleccionada")
    @ApiResponse(responseCode = "204", description = "Cita cancelada exitosamente")
    @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        citaMedicaService.eliminarCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
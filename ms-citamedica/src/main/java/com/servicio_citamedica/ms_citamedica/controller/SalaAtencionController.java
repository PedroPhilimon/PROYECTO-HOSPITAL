package com.servicio_citamedica.ms_citamedica.controller;

import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionResponseDTO;
import com.servicio_citamedica.ms_citamedica.service.SalaAtencionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@Tag(name = "Gestión Salas del hospital", description = "Gestiona todas las salas de un hospital")
public class SalaAtencionController {

    @Autowired
    private SalaAtencionService salaAtencionService;

    @PostMapping
    @Operation(summary = "Crear una sala", description = "Crea una sala con todos sus detalles")
    @ApiResponse(responseCode = "201", description = "Sala Creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al crear la Sala")
    public ResponseEntity<SalaAtencionResponseDTO> crearSala(@RequestBody SalaAtencionRequestDTO requestDTO) {
        SalaAtencionResponseDTO responseDTO = salaAtencionService.create(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de una sala específica", description = "Busca y retorna la información de una sala específica por su ID")
    @ApiResponse(responseCode = "200", description = "Sala encontrada")
    @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    public ResponseEntity<SalaAtencionResponseDTO> obtenerSalaId(@PathVariable Long id) {
        SalaAtencionResponseDTO responseDTO = salaAtencionService.findById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar todas las salas", description = "Retorna todas las salas registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<SalaAtencionResponseDTO>> listarSalas() {
        List<SalaAtencionResponseDTO> citas = salaAtencionService.findAll();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar una sala específica", description = "Modificar una sala existente según su id")
    @ApiResponse(responseCode = "200", description = "Sala modificada exitosamente")
    @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    public ResponseEntity<SalaAtencionResponseDTO> actualizarSala(@PathVariable Long id, @RequestBody SalaAtencionRequestDTO requestDTO) {
        SalaAtencionResponseDTO responseDTO = salaAtencionService.update(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar una cita médica", description = "Elimina o cancela el registro de la cita seleccionada")
    @ApiResponse(responseCode = "204", description = "Cita cancelada exitosamente")
    @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    public ResponseEntity<Void> eliminarSala(@PathVariable Long id) {
        salaAtencionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
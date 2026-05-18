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

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDTO>> findAll() {
        List<EspecialidadResponseDTO> especialidades = especialidadService.findAll();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> findByDto(@PathVariable Long id) {
        EspecialidadResponseDTO buscarMedicoId = especialidadService.findById(id);
        return ResponseEntity.ok(buscarMedicoId);
    }

    @PostMapping
    public ResponseEntity<EspecialidadResponseDTO> create(@Valid @RequestBody EspecialidadRequestDTO dto) {
        EspecialidadResponseDTO crearMedico = especialidadService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearMedico);
    }


}

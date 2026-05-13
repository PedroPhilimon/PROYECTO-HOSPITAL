package com.servicio_medicos.ms_medicos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_medicos.ms_medicos.Service.MedicoService;
import com.servicio_medicos.ms_medicos.dto.MedicoRequestDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> findAll() {
        List<MedicoResponseDTO> medicos = medicoService.findAll();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> findByDto(@PathVariable Long id) {
        MedicoResponseDTO buscarMedicoId = medicoService.findById(id);
        return ResponseEntity.ok(buscarMedicoId);
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> create(@Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO crearMedico = medicoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearMedico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO actualizarMedico = medicoService.update(id, dto);
        return ResponseEntity.ok(actualizarMedico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

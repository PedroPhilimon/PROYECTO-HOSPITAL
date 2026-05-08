package com.servicio_medicos.ms_medicos.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_medicos.ms_medicos.Service.MedicoService;
import com.servicio_medicos.ms_medicos.dto.MedicoDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/medicos")
public class MedicoController {
    // 1. Cambiamos a 'private final' y le damos un nombre consistente
    private final MedicoService medicoService; 

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> getMedicoById(@PathVariable Long id) {
        // 2. Usamos el punto '.' para llamar al método
        return ResponseEntity.ok(medicoService.findById(id));
    } 
}
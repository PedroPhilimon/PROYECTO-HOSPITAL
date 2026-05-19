package com.servicio_consultas.ms_consultas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_consultas.ms_consultas.dto.ConsultaRequestDTO;
import com.servicio_consultas.ms_consultas.dto.ConsultaResponseDTO;
import com.servicio_consultas.ms_consultas.model.Consulta;
import com.servicio_consultas.ms_consultas.service.ConsultaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/consultas")
@RequiredArgsConstructor 
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> registrarConsulta(@Valid @RequestBody ConsultaRequestDTO dto) {
        Consulta nuevaConsulta = consultaService.registrarConsulta(dto);
        
        ConsultaResponseDTO response = ConsultaResponseDTO.builder()
                .id(nuevaConsulta.getId())
                .pacienteId(nuevaConsulta.getPacienteId())
                .medicoId(nuevaConsulta.getMedicoId())
                .citaId(nuevaConsulta.getCitaId())
                .fecha(nuevaConsulta.getFecha())
                .motivaConsulta(nuevaConsulta.getMotivoConsulta())
                .diagnostico(nuevaConsulta.getDiagnostico())
                .observaciones(nuevaConsulta.getObservaciones())
                .build();
                
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {        
        Consulta consulta = consultaService.buscarPorId(id);
    
        ConsultaResponseDTO response = ConsultaResponseDTO.builder()
            .id(consulta.getId())
            .pacienteId(consulta.getPacienteId())
            .medicoId(consulta.getMedicoId())
            .citaId(consulta.getCitaId())
            .fecha(consulta.getFecha())
            .motivaConsulta(consulta.getMotivoConsulta())
            .diagnostico(consulta.getDiagnostico())
            .observaciones(consulta.getObservaciones())
            .build();
            
        return ResponseEntity.ok(response);
        
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id) {
        consultaService.eliminarConsulta(id);

        return ResponseEntity.noContent().build();
    }
}
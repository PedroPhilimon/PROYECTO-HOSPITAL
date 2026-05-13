package com.servicio_citamedica.ms_citamedica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.model.CitaMedica;
import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;
import com.servicio_citamedica.ms_citamedica.repository.CitaMedicaRepository;
import com.servicio_citamedica.ms_citamedica.service.CitaMedicaService;
import com.servicio_citamedica.ms_citamedica.service.SalaAtencionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaMedicaServiceImpl implements CitaMedicaService {
    
    private final CitaMedicaRepository citaMedicaRepository;
    private final SalaAtencionService salaAtencionService; // Inyectamos el servicio de salas para validar

    @Override
    public List<CitaResponseDTO> findAll() {
        return citaMedicaRepository.findAll()
                .stream()
                .map(CitaResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    @Override
    public CitaResponseDTO findByDto(Long id) {
        return citaMedicaRepository.findById(id)
                .map(CitaResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("No se encontró la cita con ID: " + id));
    }

    @Override
    public CitaResponseDTO create(CitaRequestDTO dto) {
        CitaMedica citamedica = new CitaMedica();
        
        // Mapeo manual de campos básicos
        actualizarCamposBasicos(citamedica, dto);

        // Validación y asignación de Sala
        if (dto.getSalaId() != null) {
            asignarSala(citamedica, dto.getSalaId());
        }

        CitaMedica guardarCita = citaMedicaRepository.save(citamedica);
        return CitaResponseDTO.fromEntity(guardarCita);
    }

    @Override
    public CitaResponseDTO update(Long id, CitaRequestDTO dto) {
        CitaMedica cita = citaMedicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    
        actualizarCamposBasicos(cita, dto);
    
        if (dto.getSalaId() != null) {
            asignarSala(cita, dto.getSalaId());
        }
    
        CitaMedica actualizada = citaMedicaRepository.save(cita);
        return CitaResponseDTO.fromEntity(actualizada);
    }

    @Override
    public void delete(Long id) {
        if (!citaMedicaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Cita no encontrada con ID: " + id);
        }   
        citaMedicaRepository.deleteById(id);
    }

    // Métodos privados para mantener el código limpio (Refactorización)
    private void actualizarCamposBasicos(CitaMedica cita, CitaRequestDTO dto) {
        cita.setPacienteId(dto.getPacienteId());
        cita.setMedicoId(dto.getMedicoId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setEstado(dto.getEstado());
        cita.setMotivo(dto.getMotivo());
    }

    private void asignarSala(CitaMedica cita, Long salaId) {
        if (!salaAtencionService.existsById(salaId)) {
            throw new RuntimeException("Error: La sala con ID " + salaId + " no existe.");
        }
        
        SalaAtencion sala = new SalaAtencion();
        sala.setId(salaId); 
        cita.setSala(sala); // Se asigna el objeto completo a la relación @ManyToOne
    }
}
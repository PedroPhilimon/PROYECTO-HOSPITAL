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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaMedicaServiceImpl implements CitaMedicaService {
    
    private final CitaMedicaRepository citaMedicaRepository;

    @Override
    public List<CitaResponseDTO> findAll() { // <--- Aquí faltaba el '>'
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

        // Seteos básicos
        citamedica.setPacienteId(dto.getPacienteId());
        citamedica.setMedicoId(dto.getMedicoId());
        citamedica.setFecha(dto.getFecha());
        citamedica.setHora(dto.getHora());
        citamedica.setEstado(dto.getEstado());
        citamedica.setMotivo(dto.getMotivo());

        // instancia de SalaAtencion y cambiarle el id
        if (dto.getSalaId() != null) {
            SalaAtencion sala = new SalaAtencion();
            sala.setId(dto.getSalaId()); 
            citamedica.setSala(sala);
        }

        CitaMedica guardarCita = citaMedicaRepository.save(citamedica);

        return CitaResponseDTO.fromEntity(guardarCita);
    }

    @Override
    public CitaResponseDTO update(Long id, CitaRequestDTO dto) {
        // 1. Buscamos la cita existente
        CitaMedica cita = citaMedicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    
        // 2. Actualizamos campos simples
        cita.setPacienteId(dto.getPacienteId());
        cita.setMedicoId(dto.getMedicoId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setEstado(dto.getEstado());
        cita.setMotivo(dto.getMotivo());
    
        if (dto.getSalaId() != null) {
            // En lugar de pasar el Long directamente, creamos el objeto SalaAtencion
            SalaAtencion sala = new SalaAtencion();
            sala.setId(dto.getSalaId());
            cita.setSala(sala); 
        }
    
        CitaMedica actualizada = citaMedicaRepository.save(cita);
    
        return CitaResponseDTO.fromEntity(actualizada);
    }

    @Override
    public void delete(Long id) {
        if (!citaMedicaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Paciente no encontrado con ID: " + id);
        }   
    
        citaMedicaRepository.deleteById(id);
    }

    


}
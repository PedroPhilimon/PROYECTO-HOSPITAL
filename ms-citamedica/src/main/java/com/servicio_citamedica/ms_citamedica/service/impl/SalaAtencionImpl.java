package com.servicio_citamedica.ms_citamedica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionResponseDTO;
import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;
import com.servicio_citamedica.ms_citamedica.repository.SalaAtencionRepository;
import com.servicio_citamedica.ms_citamedica.service.SalaAtencionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaAtencionImpl implements SalaAtencionService {
    
    private final SalaAtencionRepository salaAtencionRepository;

    @Override
    public List<SalaAtencionResponseDTO> findAll() {
        return salaAtencionRepository.findAll()
                .stream()
                .map(SalaAtencionResponseDTO::fromEntity) // Usa el método estático de tu DTO
                .collect(Collectors.toList());
    }

    @Override
    public SalaAtencionResponseDTO findById(Long id) {
        return salaAtencionRepository.findById(id)
                .map(SalaAtencionResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada con ID: " + id));
    }

    @Override
    public SalaAtencionResponseDTO create(SalaAtencionRequestDTO dto) {
        SalaAtencion sala = new SalaAtencion();
        sala.setNombre(dto.getNombre());
        sala.setUbicacion(dto.getUbicacion());
        
        SalaAtencion guardada = salaAtencionRepository.save(sala);
        return SalaAtencionResponseDTO.fromEntity(guardada);
    }

    @Override
    public SalaAtencionResponseDTO update(Long id, SalaAtencionRequestDTO dto) {
        SalaAtencion sala = salaAtencionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Sala no encontrada"));

        sala.setNombre(dto.getNombre());
        sala.setUbicacion(dto.getUbicacion());

        SalaAtencion actualizada = salaAtencionRepository.save(sala);
        return SalaAtencionResponseDTO.fromEntity(actualizada);
    }

    @Override
    public void delete(Long id) {
        if (!salaAtencionRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Sala no encontrada");
        }
        salaAtencionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return salaAtencionRepository.existsById(id);
    }
}

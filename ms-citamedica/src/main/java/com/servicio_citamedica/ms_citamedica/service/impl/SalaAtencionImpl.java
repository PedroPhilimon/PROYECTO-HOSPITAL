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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaAtencionImpl implements SalaAtencionService {
    
    private final SalaAtencionRepository salaAtencionRepository;

    @Override
    public List<SalaAtencionResponseDTO> findAll() {
        log.info("Obteniendo todas las salas de atención...");
        return salaAtencionRepository.findAll()
                .stream()
                .map(SalaAtencionResponseDTO::fromEntity) // Usa el método estático de tu DTO
                .collect(Collectors.toList());
    }

    @Override
    public SalaAtencionResponseDTO findById(Long id) {
        log.info("Buscando sala de atención con ID: {}", id);
        return salaAtencionRepository.findById(id)
                .map(SalaAtencionResponseDTO::fromEntity)
                .orElseThrow(() -> {
                    log.error("No se encontró la sala de atención con ID: {}", id);
                    return new RuntimeException("Sala no encontrada con ID: " + id);
                });
    }

    @Override
    public SalaAtencionResponseDTO create(SalaAtencionRequestDTO dto) {
        log.info("Creando nueva sala de atención con nombre: {}", dto.getNombre());
        SalaAtencion sala = new SalaAtencion();
        sala.setNombre(dto.getNombre());
        sala.setUbicacion(dto.getUbicacion());
        
        SalaAtencion guardada = salaAtencionRepository.save(sala);
        log.info("Sala de atención creada exitosamente con ID: {}", guardada.getId());
        return SalaAtencionResponseDTO.fromEntity(guardada);
    }

    @Override
    public SalaAtencionResponseDTO update(Long id, SalaAtencionRequestDTO dto) {
        log.info("Actualizando sala de atención con ID: {}", id);
        SalaAtencion sala = salaAtencionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: Sala de atención no encontrada con ID: {}", id);
                    return new RuntimeException("No se puede actualizar: Sala no encontrada");
                });

        sala.setNombre(dto.getNombre());
        sala.setUbicacion(dto.getUbicacion());

        SalaAtencion actualizada = salaAtencionRepository.save(sala);
        log.info("Sala de atención con ID: {} actualizada exitosamente", id);
        return SalaAtencionResponseDTO.fromEntity(actualizada);
    }

    @Override
    public void delete(Long id) {
        log.info("Intentando eliminar sala de atención con ID: {}", id);
        if (!salaAtencionRepository.existsById(id)) {
            log.error("Error al eliminar: Sala de atención no encontrada con ID: {}", id);
            throw new RuntimeException("No se puede eliminar: Sala no encontrada");
        }
        salaAtencionRepository.deleteById(id);
        log.info("Sala de atención con ID: {} eliminada exitosamente", id);
    }

    @Override
    public boolean existsById(Long id) {
        log.info("Verificando existencia de la sala de atención con ID: {}", id);
        return salaAtencionRepository.existsById(id);
    }
}

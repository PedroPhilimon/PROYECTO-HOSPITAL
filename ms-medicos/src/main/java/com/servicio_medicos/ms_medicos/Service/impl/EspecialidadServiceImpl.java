package com.servicio_medicos.ms_medicos.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.servicio_medicos.ms_medicos.Repository.EspecialidadRepository;
import com.servicio_medicos.ms_medicos.Service.EspecialidadService;
import com.servicio_medicos.ms_medicos.dto.EspecialidadRequestDTO;
import com.servicio_medicos.ms_medicos.dto.EspecialidadResponseDTO;
import com.servicio_medicos.ms_medicos.model.Especialidad;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class EspecialidadServiceImpl implements EspecialidadService  {

    private final EspecialidadRepository especialidadRepository;
    @Override
    @Transactional
    public EspecialidadResponseDTO create(EspecialidadRequestDTO dto) {
        log.info("Iniciando creación de especialidad con nombre: {}",dto.getNombre());
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(dto.getNombre());

        Especialidad guardada = especialidadRepository.save(especialidad);
        
        EspecialidadResponseDTO response = new EspecialidadResponseDTO();
        response.setId(guardada.getId());
        response.setNombre(guardada.getNombre());
        
        return response;
    }

    @Override
    @Transactional
    public EspecialidadResponseDTO findById(Long id) {
        log.info("Buscando especialidad con ID: {}", id);
        Especialidad especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> {
                    log.error("Especialidad no encontrada con ID: {}", id);
                    return new RuntimeException(
                            "Especialidad no encontrada con ID: " + id);
                });
        
        log.info("Especialidad encontrada con ID: {}", id);

        EspecialidadResponseDTO response = new EspecialidadResponseDTO();
        response.setId(especialidad.getId());
        response.setNombre(especialidad.getNombre());
        return response;
    }

    @Override
    @Transactional
    public List<EspecialidadResponseDTO> findAll() {
        log.info("Obteniendo listado de especialidades");
        return especialidadRepository.findAll().stream()
            .map(e -> {
                EspecialidadResponseDTO dto = new EspecialidadResponseDTO();
                dto.setId(e.getId());
                dto.setNombre(e.getNombre());
                return dto;
            }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Intentando eliminar especialidad con ID: {}", id);
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("No existe la especialidad a eliminar");
        }
        especialidadRepository.deleteById(id);
        log.info("Especialidad eliminada correctamente con ID: {}", id);
    }
}

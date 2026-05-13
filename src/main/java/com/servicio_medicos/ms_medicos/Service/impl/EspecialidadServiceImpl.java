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
@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService  {

    private final EspecialidadRepository especialidadRepository;
    @Override
    @Transactional
    public EspecialidadResponseDTO create(EspecialidadRequestDTO dto) {
        
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
        Especialidad especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));

        EspecialidadResponseDTO response = new EspecialidadResponseDTO();
        response.setId(especialidad.getId());
        response.setNombre(especialidad.getNombre());
        return response;
    }

    @Override
    @Transactional
    public List<EspecialidadResponseDTO> findAll() {
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
        
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("No existe la especialidad a eliminar");
        }
        especialidadRepository.deleteById(id);
    }
}

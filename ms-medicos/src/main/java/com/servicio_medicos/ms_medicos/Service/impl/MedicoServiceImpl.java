package com.servicio_medicos.ms_medicos.Service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicio_medicos.ms_medicos.Repository.MedicoRepository;
import com.servicio_medicos.ms_medicos.Repository.EspecialidadRepository;
import com.servicio_medicos.ms_medicos.Service.MedicoService;
import com.servicio_medicos.ms_medicos.dto.MedicoRequestDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;
import com.servicio_medicos.ms_medicos.model.Medico;
import com.servicio_medicos.ms_medicos.model.Especialidad;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicoServiceImpl implements MedicoService {
    
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional
    public MedicoResponseDTO create(MedicoRequestDTO dto) {
        log.info("Iniciando creación de médico con email: {}", dto.getEmail());
        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId()) // Usando el ID del DTO para la especialidad
            .orElseThrow(() -> {
                    log.error("Especialidad no encontrada con ID: {}",
                            dto.getEspecialidadId());
                    return new RuntimeException("La especialidad no existe");
                });

        Medico medico = new Medico();
        medico.setNombre(dto.getNombre());
        medico.setApellido(dto.getApellido());
        medico.setEmail(dto.getEmail());
        medico.setNumero(dto.getNumero());
        medico.setEspecialidad(especialidad);

        return MedicoResponseDTO.fromEntity(medicoRepository.save(medico));
        
    }

    @Override
    @Transactional
    public MedicoResponseDTO findById(Long id) {
        log.info("Buscando médico con ID: {}", id);
        return medicoRepository.findById(id)
            .map(MedicoResponseDTO::fromEntity)
            .orElseThrow(() -> {
                    log.error("Médico no encontrado con ID: {}", id);
                    return new RuntimeException(
                            "Médico no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional
    public List<MedicoResponseDTO> findAll() {
        log.info("Obteniendo listado de todos los médicos");
        return medicoRepository.findAll().stream()
            .map(MedicoResponseDTO::fromEntity)
            .collect(Collectors.toList());
            
    }

    @Override
    @Transactional
    public MedicoResponseDTO update(Long id, MedicoRequestDTO dto) {
        log.info("Actualizando médico con ID: {}", id);
        Medico medicoExistente = medicoRepository.findById(id)
            .orElseThrow(() -> {
                    log.error("No se encontró el médico con ID: {}", id);
                    return new RuntimeException("Médico no encontrado");
                });

        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
            .orElseThrow(() -> {
                    log.error("Especialidad no encontrada con ID: {}",
                            dto.getEspecialidadId());
                    return new RuntimeException("La especialidad no existe");
                });

        medicoExistente.setNombre(dto.getNombre());
        medicoExistente.setApellido(dto.getApellido());
        medicoExistente.setEmail(dto.getEmail());
        medicoExistente.setNumero(dto.getNumero());
        medicoExistente.setEspecialidad(especialidad);

        return MedicoResponseDTO.fromEntity(medicoRepository.save(medicoExistente));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Intentando eliminar médico con ID: {}", id);
        if (!medicoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Médico no encontrado");
        }
        medicoRepository.deleteById(id);
    }

    
}
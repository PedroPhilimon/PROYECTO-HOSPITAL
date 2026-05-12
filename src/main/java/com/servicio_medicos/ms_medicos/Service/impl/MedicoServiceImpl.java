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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {
    
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional
    public MedicoResponseDTO create(MedicoRequestDTO dto) {
        // Lógica: Validar que la especialidad exista
        Especialidad especialidad = especialidadRepository.findById(dto.getId()) // Usando el ID del DTO para la especialidad
            .orElseThrow(() -> new RuntimeException("La especialidad no existe"));

        // Lógica: Mapear DTO a Entidad
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
        return medicoRepository.findById(id)
            .map(MedicoResponseDTO::fromEntity)
            .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public List<MedicoResponseDTO> findAll() {
        return medicoRepository.findAll().stream()
            .map(MedicoResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MedicoResponseDTO update(Long id, MedicoRequestDTO dto) {
        Medico medicoExistente = medicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        Especialidad especialidad = especialidadRepository.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("La especialidad no existe"));

        // Actualizar campos
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
        if (!medicoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Médico no encontrado");
        }
        medicoRepository.deleteById(id);
    }
}
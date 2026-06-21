package com.pacientes.servicio_pacientes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.dto.PacienteResponseDTO;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.repository.PacienteRepository;
import com.pacientes.servicio_pacientes.service.PacienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    public List<PacienteResponseDTO> findAll() {
        log.info("Obteniendo la lista de todos los pacientes desde la base de datos");
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponseDTO findByDto(Long id) {
        log.info("Buscando paciente con ID: {}", id);
        return pacienteRepository.findById(id)
                .map(PacienteResponseDTO::fromEntity)
                .orElseThrow(() -> {
                    // Usamos warn aquí porque es un error de negocio/usuario (no encontró el ID), no un fallo crítico del sistema
                    log.warn("Búsqueda fallida: No se encontró el paciente con ID: {}", id);
                    return new RuntimeException("No se encontró el paciente con ID: " + id);
                });
    }

    @Override
    public PacienteResponseDTO create(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        
        paciente.setRun(dto.getRun()); 
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setPrevision(dto.getPrevision());

        Paciente guardarPaciente = pacienteRepository.save(paciente);
        log.info("Paciente creado exitosamente con ID: {}", guardarPaciente.getId());

        //Convertir la entidad guardada de vuelta a DTO para la respuesta
        return PacienteResponseDTO.fromEntity(guardarPaciente);
    }

   @Override
    public PacienteResponseDTO update(Long id, PacienteRequestDTO dto) {
        log.info("Iniciando actualización para el paciente con ID: {}", id);
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Actualización fallida: Paciente no encontrado con ID: {}", id);
                    return new RuntimeException("No se puede actualizar: Paciente no encontrado con ID: " + id);
                });

        paciente.setRun(dto.getRun()); 
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setPrevision(dto.getPrevision());

       
        Paciente actualizarPaciente = pacienteRepository.save(paciente);
        log.info("Paciente con ID: {} actualizado exitosamente", id);
        
        return PacienteResponseDTO.fromEntity(actualizarPaciente);
    }


    @Override
    public void delete(Long id) {
        log.info("Iniciando proceso de eliminación para el paciente con ID: {}", id);
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Paciente no encontrado con ID: " + id);
        }   
    
        // 2. Eliminar de la base de datos
        pacienteRepository.deleteById(id);
        log.info("Paciente con ID {} eliminado exitosamente", id);
    }
    
}

    


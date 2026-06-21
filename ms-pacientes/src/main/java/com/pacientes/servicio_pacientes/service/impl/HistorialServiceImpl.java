package com.pacientes.servicio_pacientes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pacientes.servicio_pacientes.dto.HistorialRequestDTO;
import com.pacientes.servicio_pacientes.dto.HistorialResponseDTO;
import com.pacientes.servicio_pacientes.model.HistorialPaciente;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.repository.HistorialPacienteRepository;
import com.pacientes.servicio_pacientes.repository.PacienteRepository;
import com.pacientes.servicio_pacientes.service.HistorialPacienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistorialServiceImpl implements HistorialPacienteService {
    private final HistorialPacienteRepository historialRepository;
    private final PacienteRepository pacienteRepository;


    @Override
    public HistorialResponseDTO create(Long pacienteId, HistorialRequestDTO dto) {
        log.info("Iniciando creación de historial para el paciente con ID: {}", pacienteId);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> {
                    log.warn("Creación de historial fallida: Paciente no encontrado con ID: {}", pacienteId);
                    return new RuntimeException("Paciente no encontrado");
                });

        //Crear la entidad y asignar datos
        HistorialPaciente historial = new HistorialPaciente();
        historial.setDiagnostico(dto.getDiagnostico());
        historial.setAntecedentes(dto.getAntecedentes());
        historial.setTipoSangre(dto.getTipoSangre());
        historial.setPaciente(paciente); // Aquí se hace la unión
        
        //Guardar y retornar
        return HistorialResponseDTO.fromEntity(historialRepository.save(historial));
        
    }


    //Buscar historial de un paciente específico
    @Override
    public List<HistorialResponseDTO> findByPacienteId(Long pacienteId) {
        log.info("Buscando historiales para el paciente con ID: {}", pacienteId);
        return historialRepository.findByPacienteId(pacienteId)
                .stream()
                .map(HistorialResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Archivo: ms-paciente/src/main/java/com/pacientes/servicio_pacientes/service/impl/HistorialServiceImpl.java
    @Override
    public void delete(Long id) {
        log.info("Iniciando proceso de eliminación para el historial con ID: {}", id);
        if (!historialRepository.existsById(id)) {
            log.warn("Intento de eliminación fallido: El historial con ID {} no existe en la base de datos", id);
            throw new RuntimeException("No se puede eliminar: Historial no encontrado con ID: " + id);
        }
        historialRepository.deleteById(id);
        log.info("Historial con ID {} eliminado exitosamente", id);
    }

    
}




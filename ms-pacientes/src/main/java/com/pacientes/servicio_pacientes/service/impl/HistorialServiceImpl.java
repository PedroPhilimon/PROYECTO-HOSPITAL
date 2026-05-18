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

@Service
@RequiredArgsConstructor
public class HistorialServiceImpl implements HistorialPacienteService {
    private final HistorialPacienteRepository historialRepository;
    private final PacienteRepository pacienteRepository;


    @Override
    public HistorialResponseDTO create(Long pacienteId, HistorialRequestDTO dto) {
        //Verificar que el paciente exista
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        //Crear la entidad y asignar datos
        HistorialPaciente historial = new HistorialPaciente();
        historial.setDiagonostico(dto.getDiagnostico());
        historial.setAntecedentes(dto.getAntecedentes());
        historial.setTipoSangre(dto.getTipoSangre());
        historial.setPaciente(paciente); // Aquí se hace la unión

        //Guardar y retornar
        return HistorialResponseDTO.fromEntity(historialRepository.save(historial));
    }


    //Buscar historial de un paciente específico
    @Override
    public List<HistorialResponseDTO> findByPacienteId(Long pacienteId) {
        // Busca en la BD y convierte cada entidad a DTO usando el método fromEntity que ya creaste
        return historialRepository.findByPacienteId(pacienteId)
                .stream()
                .map(HistorialResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Archivo: ms-paciente/src/main/java/com/pacientes/servicio_pacientes/service/impl/HistorialServiceImpl.java
    @Override
    public void delete(Long id) {
        if (!historialRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Historial no encontrado con ID: " + id);
        }
        historialRepository.deleteById(id);
    }

    
}




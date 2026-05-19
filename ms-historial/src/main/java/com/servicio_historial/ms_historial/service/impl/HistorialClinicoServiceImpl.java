package com.servicio_historial.ms_historial.service.impl;

import com.servicio_historial.ms_historial.client.MedicoClient;
import com.servicio_historial.ms_historial.client.PacienteClient;
import com.servicio_historial.ms_historial.dto.HistorialClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.HistorialClinicoResponseDTO;
import com.servicio_historial.ms_historial.model.HistorialClinico;
import com.servicio_historial.ms_historial.repository.HistorialClinicoRepository;
import com.servicio_historial.ms_historial.service.HistorialClinicoService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor               
public class HistorialClinicoServiceImpl implements HistorialClinicoService {

    private final HistorialClinicoRepository repository;
    private final PacienteClient pacienteClient;
    private final MedicoClient medicoClient;

    @Override
    public List<HistorialClinicoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(HistorialClinicoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HistorialClinicoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(HistorialClinicoResponseDTO::fromEntity);
    }

    @Override
    public HistorialClinicoResponseDTO guardar(HistorialClinicoRequestDTO dto) {
        // 1. Validar la existencia del Paciente mediante OpenFeign
        try {
            pacienteClient.buscarPorId(dto.getIdPaciente());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Error de negocio: El paciente con ID " + dto.getIdPaciente() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación: No se pudo verificar el paciente.");
        }

        try {
            medicoClient.buscarPorId(dto.getIdMedico());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Error de negocio: El médico con ID " + dto.getIdMedico() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación: No se pudo verificar el médico.");
        }

        HistorialClinico historial = new HistorialClinico();
        historial.setIdPaciente(dto.getIdPaciente());
        historial.setIdMedico(dto.getIdMedico());
        historial.setDiagnostico(dto.getDiagnostico());
        historial.setTratamiento(dto.getTratamiento());
        historial.setFechaAtencion(LocalDateTime.now()); 
        HistorialClinico guardado = repository.save(historial);
        return HistorialClinicoResponseDTO.fromEntity(guardado);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("El historial clínico con ID " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}
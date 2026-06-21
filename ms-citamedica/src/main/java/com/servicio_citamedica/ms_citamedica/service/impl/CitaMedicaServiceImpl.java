package com.servicio_citamedica.ms_citamedica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.servicio_citamedica.ms_citamedica.client.MedicoClient;
import com.servicio_citamedica.ms_citamedica.client.PacienteClient;
import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.model.CitaMedica;
import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;
import com.servicio_citamedica.ms_citamedica.repository.CitaMedicaRepository;
import com.servicio_citamedica.ms_citamedica.service.CitaMedicaService;
import com.servicio_citamedica.ms_citamedica.service.SalaAtencionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitaMedicaServiceImpl implements CitaMedicaService {
    
    private final CitaMedicaRepository citaMedicaRepository;
    private final SalaAtencionService salaAtencionService;
    private final PacienteClient pacienteClient;
    private final MedicoClient medicoClient;

    @Override
    public List<CitaResponseDTO> findAll() {
        log.info("Obteniendo todas las citas médicas...");
        return citaMedicaRepository.findAll()
                .stream()
                .map(CitaResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    @Override
    public CitaResponseDTO findByDto(Long id) {
        log.info("Buscando cita médica con ID: {}", id);
        return citaMedicaRepository.findById(id)
                .map(CitaResponseDTO::fromEntity)
                .orElseThrow(() -> {
                    log.error("No se encontró la cita con ID: {}", id);
                    return new RuntimeException("No se encontró la cita con ID: " + id);
                });
    }

    @Override
    public CitaResponseDTO create(CitaRequestDTO dto) {
        log.info("Iniciando creación de cita médica para paciente ID: {} y médico ID: {}", dto.getPacienteId(), dto.getMedicoId());
        validarExistenciaExterna(dto.getPacienteId(), dto.getMedicoId());

        CitaMedica citamedica = new CitaMedica();
        actualizarCamposBasicos(citamedica, dto);

        if (dto.getSalaId() != null) {
            asignarSala(citamedica, dto.getSalaId());
        }

        return CitaResponseDTO.fromEntity(citaMedicaRepository.save(citamedica));
    }

    @Override
    public CitaResponseDTO update(Long id, CitaRequestDTO dto) {
        log.info("Actualizando cita médica con ID: {}", id);
        CitaMedica cita = citaMedicaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: Cita no encontrada con ID: {}", id);
                    return new RuntimeException("Cita no encontrada con ID: " + id);
                });
    
        validarExistenciaExterna(dto.getPacienteId(), dto.getMedicoId());
        actualizarCamposBasicos(cita, dto);
    
        if (dto.getSalaId() != null) {
            asignarSala(cita, dto.getSalaId());
        }
    
        return CitaResponseDTO.fromEntity(citaMedicaRepository.save(cita));
    }

    @Override
    public void delete(Long id) {
        log.info("Intentando eliminar cita médica con ID: {}", id);
        if (!citaMedicaRepository.existsById(id)) {
            log.error("Error al eliminar: Cita no encontrada con ID: {}", id);
            throw new RuntimeException("No se puede eliminar: Cita no encontrada con ID: " + id);
        }   
        citaMedicaRepository.deleteById(id);
        log.info("Cita médica con ID: {} eliminada exitosamente", id);
    }

    private void validarExistenciaExterna(Long pacienteId, Long medicoId) {
        log.info("Validando existencia del paciente con ID: {}", pacienteId);
        try {
            pacienteClient.buscarPorId(pacienteId);
        } catch (Exception e) {
            log.error("Validación fallida el paciente con ID: {}", medicoId);
            throw new RuntimeException("Error: El paciente con ID " + pacienteId + " no existe.");
        }

        try {
            medicoClient.buscarPorId(medicoId);
        } catch (Exception e) {
            log.error("Validación fallida: El médico con ID {} no existe.", pacienteId, e);
            throw new RuntimeException("Error: El médico con ID " + medicoId + " no existe.");
        }
    }

    private void actualizarCamposBasicos(CitaMedica cita, CitaRequestDTO dto) {
        cita.setPacienteId(dto.getPacienteId());
        cita.setMedicoId(dto.getMedicoId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setEstado(dto.getEstado());
        cita.setMotivo(dto.getMotivo());
    }

    private void asignarSala(CitaMedica cita, Long salaId) {
        log.info("Asignando sala con ID: {} a la cita médica", salaId);
        if (!salaAtencionService.existsById(salaId)) {
            log.error("Asignación de sala fallida: La sala con ID {} no existe", salaId);
            throw new RuntimeException("Error: La sala con ID " + salaId + " no existe.");
        }
        
        SalaAtencion sala = new SalaAtencion();
        sala.setId(salaId); 
        cita.setSala(sala);
    }
}
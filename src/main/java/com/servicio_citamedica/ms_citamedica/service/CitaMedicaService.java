package com.servicio_citamedica.ms_citamedica.service;

import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;
import com.servicio_citamedica.ms_citamedica.model.CitaMedica;
import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;
import com.servicio_citamedica.ms_citamedica.repository.CitaMedicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaMedicaService {

    private final CitaMedicaRepository citaMedicaRepository;

    
    public List<CitaResponseDTO> listarTodas() {
        return citaMedicaRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    
    public CitaResponseDTO obtenerPorId(Long id) {
        CitaMedica cita = citaMedicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita con ID " + id + " no encontrada"));
        return mapToResponseDTO(cita);
    }


    public CitaResponseDTO crearCita(CitaRequestDTO request) {
        CitaMedica nuevaCita = new CitaMedica();
        nuevaCita.setPacienteId(request.getPacienteId());
        nuevaCita.setMedicoId(request.getMedicoId());
        nuevaCita.setFecha(request.getFecha());
        nuevaCita.setHora(request.getHora());
        nuevaCita.setEstado(request.getEstado());
        nuevaCita.setMotivo(request.getMotivo());
        

        CitaMedica guardada = citaMedicaRepository.save(nuevaCita);
        return mapToResponseDTO(guardada);
    }


    private CitaResponseDTO mapToResponseDTO(CitaMedica cita) {
        return CitaResponseDTO.builder()
                .id(cita.getId())
                .pacienteId(cita.getPacienteId())
                .medicoId(cita.getMedicoId())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .estado(cita.getEstado())
                .motivo(cita.getMotivo())
                .nombreSala(cita.getSala() != null ? cita.getSala().getNombre() : "No asignada")
                .build();
    }
}
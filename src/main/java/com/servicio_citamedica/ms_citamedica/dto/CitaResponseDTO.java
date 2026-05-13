package com.servicio_citamedica.ms_citamedica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

import com.servicio_citamedica.ms_citamedica.model.CitaMedica;
import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;

@Data
@Builder
@AllArgsConstructor
public class CitaResponseDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private String motivo;
    private SalaAtencion nombreSala; // Extraído de SalaAtencion

    public static CitaResponseDTO fromEntity(CitaMedica cita) {
    return CitaResponseDTO.builder()
            .id(cita.getId())
            .pacienteId(cita.getPacienteId())
            .medicoId(cita.getMedicoId())
            .fecha(cita.getFecha())
            .hora(cita.getHora())
            .estado(cita.getEstado())
            .motivo(cita.getMotivo())
            .nombreSala(cita.getSala())
            .build();
}
}
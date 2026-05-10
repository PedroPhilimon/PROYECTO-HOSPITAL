package com.servicio_citamedica.ms_citamedica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private String nombreSala; // Extraído de SalaAtencion
}
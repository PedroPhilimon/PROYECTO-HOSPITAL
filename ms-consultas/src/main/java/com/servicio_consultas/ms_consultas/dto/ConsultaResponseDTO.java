package com.servicio_consultas.ms_consultas.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConsultaResponseDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private Long citaId;
    private LocalDate fecha;
    private String motivaConsulta;
    private String diagnostico;
    private String observaciones;    
}

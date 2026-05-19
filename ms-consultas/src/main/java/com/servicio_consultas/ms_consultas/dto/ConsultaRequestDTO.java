package com.servicio_consultas.ms_consultas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConsultaRequestDTO {
    
    private Long id;

    private Long pacienteId;

    private Long medicoId;

    private Long citaId;

    @FutureOrPresent
    private LocalDate fecha;

    @NotBlank(message = "El motivo de la consulta no puede estar vacío")
    private String motivoConsulta;

    @NotBlank(message = "El diagnostico no puede estar vacío")
    private String diagnostico;

    @NotBlank(message = "El campo de observaciones no puede estar vacío")
    private String observaciones;
}

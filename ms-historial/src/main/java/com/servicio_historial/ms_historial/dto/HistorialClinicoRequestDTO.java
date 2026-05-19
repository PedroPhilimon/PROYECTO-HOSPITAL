package com.servicio_historial.ms_historial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistorialClinicoRequestDTO {

    @NotNull(message = "El id del paciente no puede ser nulo")
    private Long idPaciente;

    @NotNull(message = "El id del médico no puede ser nulo")
    private Long idMedico;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String diagnostico;

    @NotBlank(message = "El tratamiento no puede estar vacío")
    private String tratamiento;
}
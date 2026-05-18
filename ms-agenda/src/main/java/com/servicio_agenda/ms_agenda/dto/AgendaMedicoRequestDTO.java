package com.servicio_agenda.ms_agenda.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgendaMedicoRequestDTO {
    private Long idAgenda;
    
    private Long idMedico;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}

package com.servicio_agenda.ms_agenda.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AsignacionSalaRequestDTO {
    private Long idAsignacion; 
    private Long idSala;
    @NotBlank(message = "El motivo del bloqueo no puede estar vacío")
    private String motivoBloqueo;
    
}

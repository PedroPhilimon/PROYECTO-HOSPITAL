package com.servicio_agenda.ms_agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionSalaRequestDTO {
 
    @NotNull(message = "El ID de la agenda es obligatorio") 
    private Long idSala;

    @NotBlank(message = "El motivo del bloqueo no puede estar vacío")
    private String motivoBloqueo;
    
}

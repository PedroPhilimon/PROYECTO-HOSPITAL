package com.servicio_laboratorio.ms_laboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamenRequestDTO {

    private Long id;

    private Long ordenId;

    @NotBlank(message = "el nombre del examen no puede estar vacío")
    private String nombreExamen;
    @NotBlank(message = "el resultado no puede estar vacío")
    private String resultado;
    @NotBlank(message = "la observación no puede estar vacía")
    private String observacion;
}

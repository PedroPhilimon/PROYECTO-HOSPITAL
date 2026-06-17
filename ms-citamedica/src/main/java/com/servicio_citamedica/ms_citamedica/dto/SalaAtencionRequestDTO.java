package com.servicio_citamedica.ms_citamedica.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaAtencionRequestDTO {

    @NotBlank(message = "Nombre de la sala es obligatorio")
    private String nombre;

    @NotBlank(message = "Ubicación de la sala es obligatoria")
    private String ubicacion;

}

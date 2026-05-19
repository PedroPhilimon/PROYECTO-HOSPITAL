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
public class RegistroClinicoRequestDTO {

    @NotBlank(message = "El peso no puede estar vacío")
    private String peso;

    @NotBlank(message = "La presión arterial no puede estar vacía")
    private String presionArterial;

    @NotBlank(message = "La temperatura no puede estar vacía")
    private String temperatura;

    @NotBlank(message = "El detalle de las observaciones no puede estar vacío")
    private String observaciones;

    @NotNull(message = "El id del historial clínico es obligatorio")
    private Long idHistorial;
}
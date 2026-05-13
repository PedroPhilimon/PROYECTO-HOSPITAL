package com.servicio_medicos.ms_medicos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadRequestDTO {

    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
}

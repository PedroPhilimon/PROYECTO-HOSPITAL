package com.servicio_medicos.ms_medicos.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadRequestDTO {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
}

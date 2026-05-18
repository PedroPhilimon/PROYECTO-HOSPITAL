package com.pacientes.servicio_pacientes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialRequestDTO {

    private Long id;
    @NotBlank(message = "El diagnostico es obligatorio")
    private String diagnostico;
    @NotBlank(message = "El detalle de los antecedentes es obligatorio")
    private String antecedentes;
    @NotBlank(message = "El tipo de sangre es obligatorio")
    private String tipoSangre;
}

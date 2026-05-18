package com.servicio_recetas.ms_recetas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecetaDTO {
    private Long idCita;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaEmision;
    private String estado;
}

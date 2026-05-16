package com.servicio_historial.ms_historial.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialClinicoDTO {
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
}
package com.servicio_historial.ms_historial.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.servicio_historial.ms_historial.model.HistorialClinico;


@Data
public class HistorialClinicoResponseDTO {
    private Long idHistorial;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;

    public static HistorialClinicoResponseDTO fromEntity(HistorialClinico historial) {
        HistorialClinicoResponseDTO dto = new HistorialClinicoResponseDTO();
        dto.setIdHistorial(historial.getIdHistorial());
        dto.setIdPaciente(historial.getIdPaciente());
        dto.setIdMedico(historial.getIdMedico());
        dto.setFechaAtencion(historial.getFechaAtencion());
        dto.setDiagnostico(historial.getDiagnostico());
        dto.setTratamiento(historial.getTratamiento());
        return dto;
    }

}
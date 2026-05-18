package com.servicio_agenda.ms_agenda.dto;

import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import lombok.Data;

@Data
public class AsignacionSalaResponseDTO {
    private Long idAsignacion;
    private Long idSala;
    private String motivoBloqueo;

    public static AsignacionSalaResponseDTO fromEntity(AsignacionSala asignacion) {
        AsignacionSalaResponseDTO dto = new AsignacionSalaResponseDTO();
        dto.setIdSala(asignacion.getIdSala());
        dto.setMotivoBloqueo(asignacion.getMotivoBloqueo());
        dto.setIdAsignacion(asignacion.getIdAsignacion());
        return dto;
    }
}
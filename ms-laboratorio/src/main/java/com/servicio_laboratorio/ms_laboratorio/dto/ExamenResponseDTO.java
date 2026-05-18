package com.servicio_laboratorio.ms_laboratorio.dto;

import com.servicio_laboratorio.ms_laboratorio.model.ExamenLaboratorio;

import lombok.Data;

@Data
public class ExamenResponseDTO {
    private Long id;
    private Long ordenId;
    private String nombreExamen;
    private String resultado;
    private String observacion;

    public static ExamenResponseDTO fromEntity(ExamenLaboratorio examen) {
        ExamenResponseDTO dto = new ExamenResponseDTO();
        dto.setId(examen.getId());
        dto.setOrdenId(examen.getOrdenId());
        dto.setNombreExamen(examen.getNombreExamen());
        dto.setResultado(examen.getResultado());
        dto.setObservacion(examen.getObservacion());
        return dto;
    }
}

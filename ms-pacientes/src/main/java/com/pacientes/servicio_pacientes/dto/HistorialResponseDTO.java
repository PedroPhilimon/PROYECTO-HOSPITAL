package com.pacientes.servicio_pacientes.dto;

import com.pacientes.servicio_pacientes.model.HistorialPaciente;

import lombok.Data;

@Data
public class HistorialResponseDTO {
    private Long id;
    private String diagnostico;
    private String antecendentes;
    private String tipoSangre;



    public static HistorialResponseDTO fromEntity(HistorialPaciente historial) {
        HistorialResponseDTO dto = new HistorialResponseDTO();
        dto.setId(historial.getId());
        dto.setDiagnostico(historial.getDiagonostico());
        dto.setAntecendentes(historial.getAntecedentes());
        dto.setTipoSangre(historial.getTipoSangre());
        return dto;
    }
}

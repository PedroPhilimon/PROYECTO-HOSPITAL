package com.servicio_laboratorio.ms_laboratorio.dto;

import java.time.LocalDate;

import com.servicio_laboratorio.ms_laboratorio.model.OrdenLaboratorio;

import lombok.Data;

@Data
public class OrdenResponseDTO {
    private Long id;
    private Long consultaId;
    private Long pacienteId;
    private Long medicoId;
    private LocalDate fecha;


    public static OrdenResponseDTO fromEntity(OrdenLaboratorio orden) {
        OrdenResponseDTO dto = new OrdenResponseDTO();
        dto.setId(orden.getId());
        dto.setConsultaId(orden.getConsultaId());
        dto.setPacienteId(orden.getPacienteId());
        dto.setMedicoId(orden.getMedicoId());
        dto.setFecha(orden.getFecha());
        return dto;
    }
}

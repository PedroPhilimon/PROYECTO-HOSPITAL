package com.servicio_agenda.ms_agenda.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.servicio_agenda.ms_agenda.model.AgendaMedico;

@Data
public class AgendaMedicoResponseDTO {
    private Long idMedico;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String estado;


    public static AgendaMedicoResponseDTO fromEntity(AgendaMedico agenda) {
        AgendaMedicoResponseDTO dto = new AgendaMedicoResponseDTO();
        dto.setIdMedico(agenda.getIdMedico());
        dto.setFechaHoraInicio(agenda.getFechaHoraInicio());
        dto.setFechaHoraFin(agenda.getFechaHoraFin());
        return dto;
    }

}
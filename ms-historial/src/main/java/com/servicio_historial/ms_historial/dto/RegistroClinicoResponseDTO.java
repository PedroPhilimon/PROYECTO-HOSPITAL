package com.servicio_historial.ms_historial.dto;

import com.servicio_historial.ms_historial.model.RegistroClinico;

import lombok.Data;

@Data
public class RegistroClinicoResponseDTO {
    private Long id;
    private String peso;
    private String presionArterial;
    private String temperatura;
    private String observaciones;

    public static RegistroClinicoResponseDTO fromEntity(RegistroClinico registro) {
        RegistroClinicoResponseDTO dto = new RegistroClinicoResponseDTO();
        dto.setId(registro.getId());
        dto.setPeso(registro.getPeso());
        dto.setPresionArterial(registro.getPresionArterial());
        dto.setTemperatura(registro.getTemperatura());
        dto.setObservaciones(registro.getObservaciones());
        return dto;
    }
}

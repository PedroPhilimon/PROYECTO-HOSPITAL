package com.servicio_citamedica.ms_citamedica.dto;

import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;

import lombok.Data;

@Data
public class SalaAtencionResponseDTO {
    private Long id;
    private String nombre;
    private String ubicacion;


    public static SalaAtencionResponseDTO fromEntity(SalaAtencion sala) {
        SalaAtencionResponseDTO dto = new SalaAtencionResponseDTO();
        dto.setId(sala.getId());
        dto.setNombre(sala.getNombre());
        dto.setUbicacion(sala.getUbicacion());
        return dto;
    }
}

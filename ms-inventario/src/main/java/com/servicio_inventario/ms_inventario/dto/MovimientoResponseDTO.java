package com.servicio_inventario.ms_inventario.dto;

import java.time.LocalDateTime;

import com.servicio_inventario.ms_inventario.model.MovimientoInventario;

import lombok.Data;

@Data
public class MovimientoResponseDTO {
    private Long id;
    private String tipoMovimiento;
    private Integer cantidad;
    private LocalDateTime fecha;
    private Long citaId;




    public static MovimientoResponseDTO fromEntity(MovimientoInventario movimiento) {
        MovimientoResponseDTO dto = new MovimientoResponseDTO();
        dto.setId(movimiento.getId());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        dto.setCantidad(movimiento.getCantidad());
        dto.setFecha(movimiento.getFecha());
        dto.setCitaId(movimiento.getCitaId());
        return dto;
    }

}

package com.servicio_facturacion.ms_facturacion.dto;

import java.time.LocalDateTime;

import com.servicio_facturacion.ms_facturacion.model.Factura;

import lombok.Data;

@Data
public class FacturaResponseDTO {
    private Long id;
    private Long citaId;
    private Long pacienteId;
    private LocalDateTime fechaEmision;
    private double montoSubtotal;
    private double montoDescuento;
    private double montoTotal;
    private String estado;
    private String medioPago;

    public static FacturaResponseDTO fromEntity(Factura factura) {
        FacturaResponseDTO dto = new FacturaResponseDTO();
        dto.setId(factura.getId());
        dto.setCitaId(factura.getCitaId());
        dto.setPacienteId(factura.getPacienteId());
        dto.setFechaEmision(factura.getFechaEmision());
        dto.setMontoSubtotal(factura.getMontoSubtotal());
        dto.setMontoDescuento(factura.getMontoDescuento());
        dto.setMontoTotal(factura.getMontoTotal());
        dto.setEstado(factura.getEstado());
        dto.setMedioPago(factura.getMedioPago());
        return dto;
    }
    
}

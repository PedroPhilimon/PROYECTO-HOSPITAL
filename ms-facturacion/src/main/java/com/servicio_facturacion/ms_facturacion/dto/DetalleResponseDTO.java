package com.servicio_facturacion.ms_facturacion.dto;

import com.servicio_facturacion.ms_facturacion.model.DetalleFactura;
import lombok.Data;

@Data
public class DetalleResponseDTO {
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double precioTotal;

    public static DetalleResponseDTO fromEntity(DetalleFactura detalleFactura) {
        DetalleResponseDTO dto = new DetalleResponseDTO();
        dto.setId(detalleFactura.getId());
        dto.setDescripcion(detalleFactura.getDescripcion());
        dto.setCantidad(detalleFactura.getCantidad());
        dto.setPrecioUnitario(detalleFactura.getPrecioUnitario());
        dto.setPrecioTotal(detalleFactura.getPrecioTotal());
        return dto;
    }
}

package com.servicio_proveedores.ms_proveedores.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import com.servicio_proveedores.ms_proveedores.model.Proveedor;

@Data
public class OrdenCompraResponseDTO {
    private Long idItemInventario;
    private Integer cantidadPedida;
    private LocalDateTime fechaPedido;
    private Double montoEstimado;
    private String estado;
    private Proveedor idProveedor;



    public static OrdenCompraResponseDTO fromEntity(OrdenCompra ordenCompra) {
        OrdenCompraResponseDTO dto = new OrdenCompraResponseDTO();
        dto.setIdItemInventario(ordenCompra.getIdItemInventario());
        dto.setCantidadPedida(ordenCompra.getCantidadPedida());
        dto.setFechaPedido(ordenCompra.getFechaPedido());
        dto.setMontoEstimado(ordenCompra.getMontoEstimado());
        dto.setEstado(ordenCompra.getEstado());
        dto.setIdProveedor(ordenCompra.getProveedor());
        return dto;
    }
}
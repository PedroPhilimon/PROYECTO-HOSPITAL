package com.servicio_proveedores.ms_proveedores.dto;

import java.time.LocalDateTime;

import com.servicio_proveedores.ms_proveedores.model.Proveedor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdenCompraRequestDTO {
    private Long idOrden;


    private Long idItemInventario;

    @Min(value = 0, message = "La cantidad pedida no puede ser negativa")
    private Integer cantidadPedida;
    private LocalDateTime fechaPedido;
    private Double montoEstimado;
    private Proveedor idProveedor;

    @NotBlank(message = "El estado de la orden ")
    private String estado;
}

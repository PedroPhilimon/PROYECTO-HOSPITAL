package com.servicio_proveedores.ms_proveedores.dto;

import java.time.LocalDateTime;

import com.servicio_proveedores.ms_proveedores.model.Proveedor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraRequestDTO {
    private Long idOrden;


    private Long idItemInventario;

    @Min(value = 0, message = "La cantidad pedida no puede ser negativa")
    private Integer cantidadPedida;
    private LocalDateTime fechaPedido;
    private Double montoEstimado;
    private Long idProveedor;

    @NotBlank(message = "El estado de la orden ")
    private String estado;
}

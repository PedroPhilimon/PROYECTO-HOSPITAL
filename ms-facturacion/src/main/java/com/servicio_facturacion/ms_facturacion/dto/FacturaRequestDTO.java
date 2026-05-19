package com.servicio_facturacion.ms_facturacion.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturaRequestDTO {
    
    private Long id;

    private Long citaId;

    private Long pacienteId;

    @NotNull(message = "La fecha de emisión no puede ser nula")
    @PastOrPresent(message = "La fecha de emisión no puede ser en el futuro")
    private LocalDateTime fechaEmision;

    @PositiveOrZero(message = "El monto subtotal debe ser mayor o igual a cero")
    private double montoSubtotal;

    @PositiveOrZero(message = "El monto de descuento debe ser mayor o igual a cero")
    private double montoDescuento;

    @PositiveOrZero(message = "El monto total debe ser mayor o igual a cero")
    private double montoTotal;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @NotBlank(message = "El medio de pago no puede estar vacío")
    private String medioPago;
}

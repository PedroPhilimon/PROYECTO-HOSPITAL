package com.servicio_inventario.ms_inventario.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimientoRequestDTO {

    private Long productoId;
    @NotBlank(message = "El tipo de movimiento no puede estar vacío")
    private String tipoMovimiento;
    @NotNull(message = "La cantidad no puede ser nula")
    private Integer cantidad;
    @FutureOrPresent 
    private LocalDateTime fecha;
    
    private Long citaId;
}

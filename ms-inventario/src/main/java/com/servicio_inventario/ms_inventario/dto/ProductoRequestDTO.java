package com.servicio_inventario.ms_inventario.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El RUN es obligatorio")
    private String nombre;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El precio no puede ser nulo")
    private Integer precio;

    @NotBlank(message = "La categoría del producto es obligatoria")
    private String categoria;

    @FutureOrPresent
    private LocalDate fechaVencimiento;
}

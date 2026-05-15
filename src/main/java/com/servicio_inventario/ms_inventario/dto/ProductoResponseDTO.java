package com.servicio_inventario.ms_inventario.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private Integer stock;
    private Integer precio;
    private String categoria;
    private LocalDate fechaNacimiento;
}

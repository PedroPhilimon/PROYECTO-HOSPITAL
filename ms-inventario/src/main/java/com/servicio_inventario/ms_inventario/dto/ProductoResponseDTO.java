package com.servicio_inventario.ms_inventario.dto;

import java.time.LocalDate;

import com.servicio_inventario.ms_inventario.model.Producto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private Integer stock;
    private Integer precio;
    private String categoria;
    private LocalDate fechaVencimiento;


    public static ProductoResponseDTO fromEntity(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setStock(producto.getStock());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());
        dto.setFechaVencimiento(producto.getFechaVencimiento());
        return dto;
    }
}

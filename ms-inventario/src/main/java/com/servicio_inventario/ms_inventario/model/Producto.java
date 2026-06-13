package com.servicio_inventario.ms_inventario.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;  

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El stock no puede ser nulo")
    @Positive(message = "El precio no puede ser negativo")
    private Integer precio;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(min = 1, max = 100, message = "La categoría debe estar entre 1 y 100")
    private String categoria;

    @Column(name = "fecha_vencimiento")
    @FutureOrPresent
    private LocalDate fechaVencimiento;
}

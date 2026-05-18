package com.servicio_inventario.ms_inventario.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movimiento_inventario")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long id;
    
    @NotBlank(message = "Se debe indicar si entra o sale del inventario")
    @Column(nullable = false)
    private String tipoMovimiento;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer cantidad;

    @NotNull(message = "la fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento")
    private LocalDateTime fecha;

    @Column(name = "id_cita")
    private Long citaId;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}

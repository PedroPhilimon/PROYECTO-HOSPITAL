package com.servicio_facturacion.ms_facturacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

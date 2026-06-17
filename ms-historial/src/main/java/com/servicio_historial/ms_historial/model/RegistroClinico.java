package com.servicio_historial.ms_historial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registros_clinicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroClinico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El peso no puede estar vacío")
    private String peso;

    @Column(name = "presion_arterial") 
    @NotBlank(message = "La presión arterial no puede estar vacía")
    private String presionArterial;

    @NotBlank(message = "La temperatura no puede estar vacía")
    private String temperatura;

    @NotBlank(message = "El detalle de las observaciones no pueden estar vacío")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial", nullable = false)
    private HistorialClinico historialClinico;
}
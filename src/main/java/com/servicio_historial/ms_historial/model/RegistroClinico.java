package com.servicio_historial.ms_historial.model;

import jakarta.persistence.*;
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
    
    private String peso;
    private String presionArterial;
    private String temperatura;
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial", nullable = false)
    private HistorialClinico historialClinico;
}
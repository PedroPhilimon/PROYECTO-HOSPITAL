package com.servicio_historial.ms_historial.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "historiales_clinicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialClinico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;
    
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
}
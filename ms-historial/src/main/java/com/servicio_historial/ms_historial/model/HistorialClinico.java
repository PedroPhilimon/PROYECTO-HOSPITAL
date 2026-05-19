package com.servicio_historial.ms_historial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    
    @NotNull(message = "El id del paciente no puede ser nulo")
    private Long idPaciente;
    @NotNull(message = "El id del médico no puede ser nulo")
    private Long idMedico;
    
    private LocalDateTime fechaAtencion;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String diagnostico;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String tratamiento;
}
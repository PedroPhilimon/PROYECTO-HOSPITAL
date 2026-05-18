package com.servicio_recetas.ms_recetas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "recetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReceta;
    
    private Long idCita;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaEmision;
    private String estado;
}
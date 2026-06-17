package com.servicio_agenda.ms_agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendas_medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    private Long idAgenda;
        
    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
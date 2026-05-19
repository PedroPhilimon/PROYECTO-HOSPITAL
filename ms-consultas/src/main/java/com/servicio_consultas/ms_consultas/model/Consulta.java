package com.servicio_consultas.ms_consultas.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consultas")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "cita_id", nullable = false)
    private Long citaId;

    @FutureOrPresent(message = "La fecha no puede ser pasada")
    private LocalDate fecha;

    @NotBlank(message = "El motivo de la consulta no puede estar vacío")
    private String motivoConsulta;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String diagnostico;

    @NotBlank(message = "El campo de observaciones no estar vacío")
    private String observaciones;

}

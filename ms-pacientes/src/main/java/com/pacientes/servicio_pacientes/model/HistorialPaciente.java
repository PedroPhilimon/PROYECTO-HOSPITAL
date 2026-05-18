package com.pacientes.servicio_pacientes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "historial_paciente")
public class HistorialPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "el diagnostico no puede estar vacío")
    private String diagonostico;
    @NotBlank(message = "el antecedente no puede estar vacío")
    private String antecedentes;
    @NotBlank(message = "El tipo de sangre no puede estar vacío")
    private String tipoSangre;
    

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

}

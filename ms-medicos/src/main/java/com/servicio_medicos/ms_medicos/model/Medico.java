package com.servicio_medicos.ms_medicos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String email;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String numero;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad")
    Especialidad especialidad;
     
}

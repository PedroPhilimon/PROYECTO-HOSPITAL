package com.servicio_laboratorio.ms_laboratorio.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orden_laboratorio")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdenLaboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long consultaId;

    private Long pacienteId;

    private Long medicoId;

    private LocalDate fecha;

    
}

package com.servicio_laboratorio.ms_laboratorio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "examen_laboratorio")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamenLaboratorio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ordenId;

    private String nombreExamen;

    private String resultado;

    private String observacion;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private OrdenLaboratorio orden;
}

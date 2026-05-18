package com.servicio_recetas.ms_recetas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReceta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idMedicamento;
    private Integer cantidadRecetada;
    private String dosificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;
}

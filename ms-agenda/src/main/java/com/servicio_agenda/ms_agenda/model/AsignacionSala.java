package com.servicio_agenda.ms_agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asignaciones_salas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionSala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;
    
    private Long idSala;
    
    @NotBlank(message = "El motivo del bloqueo no puede estar vacío")
    private String motivoBloqueo;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private AgendaMedico agendaMedico;
}

package com.servicio_laboratorio.ms_laboratorio.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdenRequestDTO {
    
    private Long id;

    private Long consultaId;

    private Long pacienteId;

    private Long medicoId;

    @FutureOrPresent
    private LocalDate fecha;



}

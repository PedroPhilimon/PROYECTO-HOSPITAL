package com.servicio_laboratorio.ms_laboratorio.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenRequestDTO {

    private Long consultaId;

    private Long pacienteId;

    private Long medicoId;

    @FutureOrPresent
    private LocalDate fecha;



}

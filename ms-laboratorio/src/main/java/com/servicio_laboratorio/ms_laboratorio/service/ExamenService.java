package com.servicio_laboratorio.ms_laboratorio.service;

import java.util.List;

import com.servicio_laboratorio.ms_laboratorio.dto.ExamenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.ExamenResponseDTO;

public interface ExamenService {

    ExamenResponseDTO crearExamen(ExamenRequestDTO examenRequestDTO);

    ExamenResponseDTO obtenerExamenPorId(Long id);

    List<ExamenResponseDTO> obtenerExamenesPorOrdenId(Long ordenId);

    List<ExamenResponseDTO> obtenerTodosLosExamenes();

}

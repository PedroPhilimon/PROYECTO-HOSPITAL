package com.pacientes.servicio_pacientes.service;

import java.util.List;


import com.pacientes.servicio_pacientes.dto.HistorialRequestDTO;
import com.pacientes.servicio_pacientes.dto.HistorialResponseDTO;

public interface HistorialPacienteService {
    
    HistorialResponseDTO create(Long pacienteId, HistorialRequestDTO dto);

    List<HistorialResponseDTO> findByPacienteId(Long pacienteId);

    void delete(Long id);


}

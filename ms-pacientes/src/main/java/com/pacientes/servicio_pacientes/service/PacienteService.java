package com.pacientes.servicio_pacientes.service;

import java.util.List;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.dto.PacienteResponseDTO;

public interface PacienteService {

    List<PacienteResponseDTO> findAll();

    PacienteResponseDTO findByDto(Long id);

    PacienteResponseDTO create(PacienteRequestDTO dto);

    PacienteResponseDTO update(Long id, PacienteRequestDTO dto);

    void delete(Long id);


}

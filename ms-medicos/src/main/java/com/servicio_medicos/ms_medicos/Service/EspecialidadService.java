package com.servicio_medicos.ms_medicos.Service;

import java.util.List;

import com.servicio_medicos.ms_medicos.dto.EspecialidadRequestDTO;
import com.servicio_medicos.ms_medicos.dto.EspecialidadResponseDTO;

public interface EspecialidadService {

    EspecialidadResponseDTO create(EspecialidadRequestDTO dto);

    EspecialidadResponseDTO findById(Long id);

    List<EspecialidadResponseDTO> findAll();

    void delete(Long id);

}

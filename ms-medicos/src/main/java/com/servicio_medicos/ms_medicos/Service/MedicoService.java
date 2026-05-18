package com.servicio_medicos.ms_medicos.Service;

import java.util.List;

import com.servicio_medicos.ms_medicos.dto.MedicoRequestDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;

public interface MedicoService {

    List<MedicoResponseDTO> findAll();

    MedicoResponseDTO create(MedicoRequestDTO dto);

    MedicoResponseDTO findById(Long id);

    MedicoResponseDTO update(Long id, MedicoRequestDTO dto);

    void delete(Long id);

}

package com.servicio_citamedica.ms_citamedica.service;

import java.util.List;

import com.servicio_citamedica.ms_citamedica.dto.CitaRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.CitaResponseDTO;

public interface CitaMedicaService {

    List<CitaResponseDTO> findAll();

    CitaResponseDTO findByDto(Long id);

    CitaResponseDTO create(CitaRequestDTO dto);

    CitaResponseDTO update(Long id, CitaRequestDTO dto);

    void delete(Long id);

}

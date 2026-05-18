package com.servicio_citamedica.ms_citamedica.service;

import java.util.List;

import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionResponseDTO;

public interface SalaAtencionService {

    List<SalaAtencionResponseDTO> findAll();

    SalaAtencionResponseDTO findById(Long id);

    SalaAtencionResponseDTO create(SalaAtencionRequestDTO dto);

    SalaAtencionResponseDTO update(Long id, SalaAtencionRequestDTO dto);

    void delete(Long id);
    //Saber si la sala existe
    boolean existsById(Long id);

}

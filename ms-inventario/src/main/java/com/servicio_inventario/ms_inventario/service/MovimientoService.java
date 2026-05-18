package com.servicio_inventario.ms_inventario.service;

import java.util.List;

import com.servicio_inventario.ms_inventario.dto.MovimientoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.MovimientoResponseDTO;


public interface MovimientoService {

    List<MovimientoResponseDTO> findAll();

    MovimientoResponseDTO findByDto(Long id);

    MovimientoResponseDTO save(MovimientoRequestDTO dto);

    MovimientoResponseDTO update(Long id, MovimientoRequestDTO dto);

    void delete(Long id);

}

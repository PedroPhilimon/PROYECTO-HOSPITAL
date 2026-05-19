package com.servicio_historial.ms_historial.service;

import java.util.List;
import java.util.Optional;

import com.servicio_historial.ms_historial.dto.HistorialClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.HistorialClinicoResponseDTO;

public interface HistorialClinicoService {

    List<HistorialClinicoResponseDTO> listarTodos();

    Optional<HistorialClinicoResponseDTO> buscarPorId(Long id);

    HistorialClinicoResponseDTO guardar(HistorialClinicoRequestDTO dto);

    void eliminar(Long id);

    
} 
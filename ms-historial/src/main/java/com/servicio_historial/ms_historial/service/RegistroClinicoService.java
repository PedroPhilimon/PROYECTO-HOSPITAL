package com.servicio_historial.ms_historial.service;

import java.util.List;
import java.util.Optional;

import com.servicio_historial.ms_historial.dto.RegistroClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.RegistroClinicoResponseDTO;

public interface RegistroClinicoService {

    List<RegistroClinicoResponseDTO> listarTodos();

    Optional<RegistroClinicoResponseDTO> buscarPorId(Long id);

    RegistroClinicoResponseDTO guardar(RegistroClinicoRequestDTO dto);

    void eliminar(Long id);
    
}

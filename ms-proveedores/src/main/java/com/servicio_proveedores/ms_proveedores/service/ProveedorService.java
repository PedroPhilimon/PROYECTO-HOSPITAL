package com.servicio_proveedores.ms_proveedores.service;

import com.servicio_proveedores.ms_proveedores.dto.ProveedorRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.ProveedorResponseDTO;
import java.util.List;

public interface ProveedorService {
    List<ProveedorResponseDTO> listarTodos();
    ProveedorResponseDTO buscarPorId(Long id);
    ProveedorResponseDTO guardar(ProveedorRequestDTO dto);
    ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto);
    void eliminar(Long id);
}
package com.servicio_proveedores.ms_proveedores.service;

import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import java.util.List;

public interface OrdenCompraService {
    List<OrdenCompraResponseDTO> listarTodas();
    OrdenCompraResponseDTO buscarPorId(Long id);
    OrdenCompraResponseDTO guardar(OrdenCompraRequestDTO dto);
    OrdenCompraResponseDTO actualizar(Long id, OrdenCompraRequestDTO dto);
    void eliminar(Long id);
}
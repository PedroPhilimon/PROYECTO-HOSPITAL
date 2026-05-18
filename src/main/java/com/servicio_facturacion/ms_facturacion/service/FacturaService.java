package com.servicio_facturacion.ms_facturacion.service;

import java.util.List;

import com.servicio_facturacion.ms_facturacion.dto.FacturaRequestDTO;
import com.servicio_facturacion.ms_facturacion.dto.FacturaResponseDTO;

public interface FacturaService {

    FacturaResponseDTO crearFactura(FacturaRequestDTO facturaRequestDTO);

    FacturaResponseDTO obtenerFacturaPorId(Long id);

    List<FacturaResponseDTO> obtenerTodasLasFacturas();

    void eliminarFactura(Long id);

}

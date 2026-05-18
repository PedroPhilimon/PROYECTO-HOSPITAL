package com.servicio_facturacion.ms_facturacion.service;

import java.util.List;

import com.servicio_facturacion.ms_facturacion.dto.DetalleResponseDTO;
import com.servicio_facturacion.ms_facturacion.model.DetalleFactura;

public interface DetalleFacturaService {

    DetalleResponseDTO agregarDetalle(Long facturaId, DetalleFactura detalleFactura);

    List<DetalleResponseDTO> obtenerDetallesPorFactura(Long facturaId);

    DetalleResponseDTO obtenerDetallePorId(Long id);

    void eliminarDetalle(Long id);

}

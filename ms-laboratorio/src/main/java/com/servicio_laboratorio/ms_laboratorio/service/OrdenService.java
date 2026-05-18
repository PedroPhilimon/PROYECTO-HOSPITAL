package com.servicio_laboratorio.ms_laboratorio.service;

import java.util.List;

import com.servicio_laboratorio.ms_laboratorio.dto.OrdenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.OrdenResponseDTO;

public interface OrdenService {

    OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO);

    OrdenResponseDTO obtenerOrdenPorId(Long id);

    List<OrdenResponseDTO> obtenerTodasLasOrdenes();

}

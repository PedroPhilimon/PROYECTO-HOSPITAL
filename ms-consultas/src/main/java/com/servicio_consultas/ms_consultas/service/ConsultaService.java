package com.servicio_consultas.ms_consultas.service;

import com.servicio_consultas.ms_consultas.dto.ConsultaRequestDTO;
import com.servicio_consultas.ms_consultas.model.Consulta;

public interface ConsultaService {

    Consulta registrarConsulta(ConsultaRequestDTO dto);

    Consulta buscarPorId(Long id);

    void eliminarConsulta(Long id);
}

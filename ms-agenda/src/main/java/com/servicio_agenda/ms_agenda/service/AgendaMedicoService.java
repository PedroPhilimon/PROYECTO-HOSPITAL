package com.servicio_agenda.ms_agenda.service;

import com.servicio_agenda.ms_agenda.dto.AgendaMedicoResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import java.util.List;
import java.util.Optional;

public interface AgendaMedicoService {
    List<AgendaMedico> listarTodas();
    Optional<AgendaMedico> buscarPorId(Long id);
    AgendaMedicoResponseDTO guardar(AgendaMedico agenda);
    void eliminar(Long id);
}
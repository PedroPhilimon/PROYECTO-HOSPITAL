package com.servicio_agenda.ms_agenda.service;

import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import java.util.List;
import java.util.Optional;

public interface AsignacionSalaService {
    List<AsignacionSala> listarTodas();
    Optional<AsignacionSala> buscarPorId(Long id);
    AsignacionSala guardar(AsignacionSala asignacion);
    void eliminar(Long id);
}
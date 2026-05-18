package com.servicio_agenda.ms_agenda.service;

import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.repository.AsignacionSalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionSalaService {

    @Autowired
    private AsignacionSalaRepository repository;

    public List<AsignacionSala> listarTodas() {
        return repository.findAll();
    }

    public Optional<AsignacionSala> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public AsignacionSala guardar(AsignacionSala asignacion) {
        return repository.save(asignacion);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
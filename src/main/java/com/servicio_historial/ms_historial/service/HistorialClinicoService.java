package com.servicio_historial.ms_historial.service;

import com.servicio_historial.ms_historial.model.HistorialClinico;
import com.servicio_historial.ms_historial.repository.HistorialClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialClinicoService {

    @Autowired
    private HistorialClinicoRepository repository;

    public List<HistorialClinico> listarTodos() {
        return repository.findAll();
    }

    public Optional<HistorialClinico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public HistorialClinico guardar(HistorialClinico historial) {
        return repository.save(historial);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
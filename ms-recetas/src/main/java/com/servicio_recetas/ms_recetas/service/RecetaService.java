package com.servicio_recetas.ms_recetas.service;

import com.servicio_recetas.ms_recetas.model.Receta;
import com.servicio_recetas.ms_recetas.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> listarTodas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> buscarPorId(Long id) {
        return recetaRepository.findById(id);
    }

    public Receta guardar(Receta receta) {
        return recetaRepository.save(receta);
    }

    public void eliminar(Long id) {
        recetaRepository.deleteById(id);
    }
}

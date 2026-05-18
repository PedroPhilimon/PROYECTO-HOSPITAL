package com.servicio_recetas.ms_recetas.controller;

import com.servicio_recetas.ms_recetas.dto.RecetaDTO;
import com.servicio_recetas.ms_recetas.model.Receta;
import com.servicio_recetas.ms_recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public List<Receta> obtenerTodas() {
        return recetaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerPorId(@PathVariable Long id) {
        return recetaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Receta crear(@RequestBody RecetaDTO dto) {
        // Pasamos los datos del DTO a la Entidad
        Receta receta = new Receta();
        receta.setIdCita(dto.getIdCita());
        receta.setIdPaciente(dto.getIdPaciente());
        receta.setIdMedico(dto.getIdMedico());
        receta.setFechaEmision(dto.getFechaEmision());
        receta.setEstado(dto.getEstado());
        
        return recetaService.guardar(receta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        recetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

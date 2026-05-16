package com.servicio_historial.ms_historial.controller;

import com.servicio_historial.ms_historial.dto.HistorialClinicoDTO;
import com.servicio_historial.ms_historial.model.HistorialClinico;
import com.servicio_historial.ms_historial.service.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoService service;

    @GetMapping
    public List<HistorialClinico> obtenerTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialClinico> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialClinico crear(@RequestBody HistorialClinicoDTO dto) {
        HistorialClinico historial = new HistorialClinico();
        historial.setIdPaciente(dto.getIdPaciente());
        historial.setIdMedico(dto.getIdMedico());
        historial.setFechaAtencion(dto.getFechaAtencion());
        historial.setDiagnostico(dto.getDiagnostico());
        historial.setTratamiento(dto.getTratamiento());
        
        return service.guardar(historial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
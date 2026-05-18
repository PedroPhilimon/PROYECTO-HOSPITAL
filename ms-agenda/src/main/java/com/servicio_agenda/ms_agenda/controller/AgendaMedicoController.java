package com.servicio_agenda.ms_agenda.controller;

import com.servicio_agenda.ms_agenda.dto.AgendaMedicoDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.service.AgendaMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaMedicoController {

    @Autowired
    private AgendaMedicoService service;

    @GetMapping
    public List<AgendaMedico> obtenerTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaMedico> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AgendaMedico crear(@RequestBody AgendaMedicoDTO dto) {
        AgendaMedico agenda = new AgendaMedico();
        agenda.setIdMedico(dto.getIdMedico());
        agenda.setFechaHoraInicio(dto.getFechaHoraInicio());
        agenda.setFechaHoraFin(dto.getFechaHoraFin());
        agenda.setEstado(dto.getEstado());
        
        return service.guardar(agenda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
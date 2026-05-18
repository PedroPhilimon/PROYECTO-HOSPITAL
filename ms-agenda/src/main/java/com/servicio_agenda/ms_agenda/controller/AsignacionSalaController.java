package com.servicio_agenda.ms_agenda.controller;

import com.servicio_agenda.ms_agenda.dto.AsignacionSalaDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.service.AgendaMedicoService;
import com.servicio_agenda.ms_agenda.service.AsignacionSalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones-salas")
public class AsignacionSalaController {

    @Autowired
    private AsignacionSalaService service;

    @Autowired
    private AgendaMedicoService agendaService;

    @GetMapping
    public List<AsignacionSala> obtenerTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignacionSala> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AsignacionSala> crear(@RequestBody AsignacionSalaDTO dto) {
        java.util.Optional<AgendaMedico> agendaOpt = agendaService.buscarPorId(dto.getIdAgenda());
        
        if (agendaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        AsignacionSala asignacion = new AsignacionSala();
        asignacion.setIdSala(dto.getIdSala());
        asignacion.setMotivoBloqueo(dto.getMotivoBloqueo());
        asignacion.setAgendaMedico(agendaOpt.get());
        
        return ResponseEntity.ok(service.guardar(asignacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
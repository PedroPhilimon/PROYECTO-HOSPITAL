package com.servicio_agenda.ms_agenda.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import com.servicio_agenda.ms_agenda.dto.AgendaMedicoResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.service.AgendaMedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendas")
public class AgendaMedicoController {

    private final AgendaMedicoService  agendaMedicoService;

    public AgendaMedicoController(AgendaMedicoService agendaMedicoService) {
        this.agendaMedicoService = agendaMedicoService;
    }

    @GetMapping
    public ResponseEntity<List<AgendaMedico>> listarTodas() {
        List<AgendaMedico> agendas = agendaMedicoService.listarTodas();
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AgendaMedico>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendaMedicoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AgendaMedicoResponseDTO> crearAgenda(@Valid @RequestBody AgendaMedico dto) {
        AgendaMedicoResponseDTO crearAgenda = agendaMedicoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAgenda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agendaMedicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
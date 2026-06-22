package com.servicio_agenda.ms_agenda.service.impl;

import com.servicio_agenda.ms_agenda.dto.AsignacionSalaRequestDTO;
import com.servicio_agenda.ms_agenda.dto.AsignacionSalaResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.repository.AgendaMedicoRepository;
import com.servicio_agenda.ms_agenda.repository.AsignacionSalaRepository;
import com.servicio_agenda.ms_agenda.service.AsignacionSalaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsignacionSalaServiceimpl implements AsignacionSalaService {

    private final AsignacionSalaRepository repository;
    private final AgendaMedicoRepository agendaRepository;
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionSala> listarTodas() {
        log.info("Obteniendo todas las asignaciones de salas...");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AsignacionSala> buscarPorId(Long id) {
        log.info("Buscando asignación de sala con ID: {}", id);
        return repository.findById(id);
    }

    @Override
    @Transactional
    public AsignacionSalaResponseDTO guardar(AsignacionSalaRequestDTO dto, Long idAgenda) {
        log.info("Iniciando guardado de asignación para la sala ID: {} vinculada a la agenda médica ID: {}", dto.getIdSala(), idAgenda);
        AgendaMedico agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> {
                    log.error("Error de asignación: La Agenda Médica con ID {} no existe.", idAgenda);
                    return new RuntimeException("La Agenda Médica con ID " + idAgenda + " no existe.");
                });

        AsignacionSala asignacion = new AsignacionSala();
        asignacion.setIdSala(dto.getIdSala());
        asignacion.setMotivoBloqueo(dto.getMotivoBloqueo());
        asignacion.setAgendaMedico(agenda);

        AsignacionSala guardada = repository.save(asignacion);
        log.info("Asignación de sala creada exitosamente con ID: {}", guardada.getIdAsignacion());

        return AsignacionSalaResponseDTO.fromEntity(guardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Intentando eliminar asignación de sala con ID: {}", id);
        repository.deleteById(id);
        log.info("Asignación de sala con ID: {} eliminada correctamente", id);
    }
}
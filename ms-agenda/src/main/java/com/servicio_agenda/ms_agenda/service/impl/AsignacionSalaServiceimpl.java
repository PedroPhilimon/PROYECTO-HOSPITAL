package com.servicio_agenda.ms_agenda.service.impl;

import com.servicio_agenda.ms_agenda.dto.AsignacionSalaRequestDTO;
import com.servicio_agenda.ms_agenda.dto.AsignacionSalaResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.repository.AgendaMedicoRepository;
import com.servicio_agenda.ms_agenda.repository.AsignacionSalaRepository;
import com.servicio_agenda.ms_agenda.service.AsignacionSalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionSalaServiceimpl implements AsignacionSalaService {

    private final AsignacionSalaRepository repository;
    private final AgendaMedicoRepository agendaRepository;
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionSala> listarTodas() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AsignacionSala> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public AsignacionSalaResponseDTO guardar(AsignacionSalaRequestDTO dto, Long idAgenda) {
        AgendaMedico agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new RuntimeException("La Agenda Médica con ID " + idAgenda + " no existe."));

        AsignacionSala asignacion = new AsignacionSala();
        asignacion.setIdSala(dto.getIdSala());
        asignacion.setMotivoBloqueo(dto.getMotivoBloqueo());
        asignacion.setAgendaMedico(agenda);

        AsignacionSala guardada = repository.save(asignacion);

        return AsignacionSalaResponseDTO.fromEntity(guardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
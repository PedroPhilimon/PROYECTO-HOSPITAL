package com.servicio_agenda.ms_agenda.service.impl;

import com.servicio_agenda.ms_agenda.client.MedicoClient;
import com.servicio_agenda.ms_agenda.dto.AgendaMedicoResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.repository.AgendaMedicoRepository;
import com.servicio_agenda.ms_agenda.service.AgendaMedicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgendaMedicoServiceimpl implements AgendaMedicoService {

    private final AgendaMedicoRepository repository;
    private final MedicoClient medicoClient;

    @Override
    @Transactional(readOnly = true)
    public List<AgendaMedico> listarTodas() {
        log.info("Obteniendo todas las agendas médicas...");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgendaMedico> buscarPorId(Long id) {
        log.info("Buscando agenda médica con ID: {}", id);
        return repository.findById(id);
    }

    @Override
    @Transactional
    public AgendaMedicoResponseDTO guardar(AgendaMedico agenda) {
        log.info("Iniciando validación y guardado de agenda médica para el médico ID: {}", agenda.getIdMedico());
        try {
            ResponseEntity<Object> respuestaMedico = medicoClient.obtenerMedicoPorId(agenda.getIdMedico());
            if (respuestaMedico.getStatusCode().isError() || respuestaMedico.getBody() == null) {
                log.error("Validación fallida: El médico con ID {} no existe en el sistema.", agenda.getIdMedico());
                throw new RuntimeException("El médico con ID " + agenda.getIdMedico() + " no existe en el sistema.");
            }
        } catch (Exception e) {
            log.error("Error de comunicación al validar el médico con ID: {}", agenda.getIdMedico(), e);
            throw new RuntimeException("Error al validar el médico. El microservicio no responde: " + e.getMessage());
        }
        
        AgendaMedico entidadGuardada = repository.save(agenda);
        log.info("Agenda médica guardada exitosamente con ID: {}", entidadGuardada.getIdAgenda());
        return AgendaMedicoResponseDTO.fromEntity(entidadGuardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando agenda médica con ID: {}", id);
        repository.deleteById(id);
        log.info("Agenda médica con ID: {} eliminada correctamente", id);
    }
}
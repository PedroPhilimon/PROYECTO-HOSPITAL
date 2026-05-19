package com.servicio_agenda.ms_agenda.service.impl;

import com.servicio_agenda.ms_agenda.client.MedicoClient;
import com.servicio_agenda.ms_agenda.dto.AgendaMedicoResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.repository.AgendaMedicoRepository;
import com.servicio_agenda.ms_agenda.service.AgendaMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendaMedicoServiceimpl implements AgendaMedicoService {

    private final AgendaMedicoRepository repository;
    private final MedicoClient medicoClient;

    @Override
    @Transactional(readOnly = true)
    public List<AgendaMedico> listarTodas() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgendaMedico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public AgendaMedicoResponseDTO guardar(AgendaMedico agenda) {
        try {
            ResponseEntity<Object> respuestaMedico = medicoClient.obtenerMedicoPorId(agenda.getIdMedico());
            if (respuestaMedico.getStatusCode().isError() || respuestaMedico.getBody() == null) {
                throw new RuntimeException("El médico con ID " + agenda.getIdMedico() + " no existe en el sistema.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el médico. El microservicio no responde: " + e.getMessage());
        }
        
        AgendaMedico entidadGuardada = repository.save(agenda);
        return AgendaMedicoResponseDTO.fromEntity(entidadGuardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
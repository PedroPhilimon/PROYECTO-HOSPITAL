package com.servicio_consultas.ms_consultas.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicio_consultas.ms_consultas.client.CitaMedicaClient;
import com.servicio_consultas.ms_consultas.client.MedicoClient;
import com.servicio_consultas.ms_consultas.client.PacienteClient;
import com.servicio_consultas.ms_consultas.dto.ConsultaRequestDTO;
import com.servicio_consultas.ms_consultas.model.Consulta;
import com.servicio_consultas.ms_consultas.repository.ConsultaRepository;
import com.servicio_consultas.ms_consultas.service.ConsultaService;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteClient pacienteClient;
    private final MedicoClient medicoClient;
    private final CitaMedicaClient citaMedicaClient;

    @Override
    @Transactional
    public Consulta registrarConsulta(ConsultaRequestDTO dto) {
        
        try {
            pacienteClient.buscarPorId(dto.getPacienteId());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Error: El paciente con ID " + dto.getPacienteId() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio de pacientes.");
        }

        try {
            medicoClient.buscarPorId(dto.getMedicoId());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Error: El médico con ID " + dto.getMedicoId() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio de médicos.");
        }

        try {
            citaMedicaClient.buscarPorId(dto.getCitaId());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Error: La cita médica con ID " + dto.getCitaId() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio de citas.");
        }

        Consulta consulta = new Consulta();
        consulta.setPacienteId(dto.getPacienteId());
        consulta.setMedicoId(dto.getMedicoId());
        consulta.setCitaId(dto.getCitaId());
        consulta.setFecha(dto.getFecha());
        consulta.setMotivoConsulta(dto.getMotivoConsulta());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setObservaciones(dto.getObservaciones());

        return consultaRepository.save(consulta);
    }


    @Override
    @Transactional(readOnly = true)
    public Consulta buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error: La consulta con ID " + id + " no existe."));
    }

    

    @Override
    @Transactional
    public void eliminarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error: No se puede eliminar. La consulta con ID " + id + " no existe."));

        consultaRepository.delete(consulta);
    }
}
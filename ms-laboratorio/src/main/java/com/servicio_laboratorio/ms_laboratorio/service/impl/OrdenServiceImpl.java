package com.servicio_laboratorio.ms_laboratorio.service.impl;

import com.servicio_laboratorio.ms_laboratorio.client.ConsultaClient;
import com.servicio_laboratorio.ms_laboratorio.client.MedicoClient;
import com.servicio_laboratorio.ms_laboratorio.client.PacienteClient;
import com.servicio_laboratorio.ms_laboratorio.dto.OrdenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.OrdenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.model.OrdenLaboratorio;
import com.servicio_laboratorio.ms_laboratorio.repository.OrdenRepository;
import com.servicio_laboratorio.ms_laboratorio.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final PacienteClient pacienteClient;
    private final MedicoClient medicoClient;
    private final ConsultaClient consultaClient;

    @Override
    @Transactional
    public OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO) {
        try {
            pacienteClient.buscarPorId(ordenRequestDTO.getPacienteId());
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el Paciente: El servicio no responde o el ID " 
                    + ordenRequestDTO.getPacienteId() + " no existe.");
        }

        try {
            medicoClient.buscarPorId(ordenRequestDTO.getMedicoId());
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el Médico: El servicio no responde o el ID " 
                    + ordenRequestDTO.getMedicoId() + " no existe.");
        }

        try {
            consultaClient.buscarPorId(ordenRequestDTO.getConsultaId());
        } catch (Exception e) {
            throw new RuntimeException("Error al validar la Consulta: El servicio no responde o el ID " 
                    + ordenRequestDTO.getConsultaId() + " no existe.");
        }

        OrdenLaboratorio orden = new OrdenLaboratorio();
        orden.setConsultaId(ordenRequestDTO.getConsultaId());
        orden.setPacienteId(ordenRequestDTO.getPacienteId());
        orden.setMedicoId(ordenRequestDTO.getMedicoId());
        orden.setFecha(ordenRequestDTO.getFecha());

        OrdenLaboratorio ordenGuardada = ordenRepository.save(orden);

        return OrdenResponseDTO.fromEntity(ordenGuardada);
    }

    @Override
    @Transactional
    public OrdenResponseDTO obtenerOrdenPorId(Long id) {
        OrdenLaboratorio orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de laboratorio no encontrada con el ID: " + id));
        return OrdenResponseDTO.fromEntity(orden);
    }

    @Override
    @Transactional
    public List<OrdenResponseDTO> obtenerTodasLasOrdenes() {
        return ordenRepository.findAll()
                .stream()
                .map(OrdenResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
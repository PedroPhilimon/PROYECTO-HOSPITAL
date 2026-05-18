package com.servicio_laboratorio.ms_laboratorio.service.impl;

import com.servicio_laboratorio.ms_laboratorio.dto.ExamenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.ExamenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.model.ExamenLaboratorio;
import com.servicio_laboratorio.ms_laboratorio.model.OrdenLaboratorio;
import com.servicio_laboratorio.ms_laboratorio.repository.ExamenRepository;
import com.servicio_laboratorio.ms_laboratorio.repository.OrdenRepository;
import com.servicio_laboratorio.ms_laboratorio.service.ExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository examenRepository;
    private final OrdenRepository ordenRepository;

    @Override
    @Transactional
    public ExamenResponseDTO crearExamen(ExamenRequestDTO examenRequestDTO) {
        OrdenLaboratorio orden = ordenRepository.findById(examenRequestDTO.getOrdenId())
                .orElseThrow(() -> new RuntimeException("No se puede crear el examen: La Orden de Laboratorio con ID " 
                        + examenRequestDTO.getOrdenId() + " no existe."));

        ExamenLaboratorio examen = new ExamenLaboratorio();
        examen.setOrdenId(examenRequestDTO.getOrdenId());
        examen.setNombreExamen(examenRequestDTO.getNombreExamen());
        examen.setResultado(examenRequestDTO.getResultado());
        examen.setObservacion(examenRequestDTO.getObservacion());
        
        examen.setOrden(orden);

        ExamenLaboratorio examenGuardado = examenRepository.save(examen);

        return ExamenResponseDTO.fromEntity(examenGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamenResponseDTO obtenerExamenPorId(Long id) {
        ExamenLaboratorio examen = examenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen de laboratorio no encontrado con el ID: " + id));
        return ExamenResponseDTO.fromEntity(examen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamenResponseDTO> obtenerExamenesPorOrdenId(Long ordenId) {
        if (!ordenRepository.existsById(ordenId)) {
            throw new RuntimeException("La Orden de Laboratorio con ID " + ordenId + " no existe.");
        }

        return examenRepository.findAll()
                .stream()
                .filter(examen -> examen.getOrdenId().equals(ordenId))
                .map(ExamenResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamenResponseDTO> obtenerTodosLosExamenes() {
        return examenRepository.findAll()
                .stream()
                .map(ExamenResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
package com.servicio_historial.ms_historial.service.impl;

import com.servicio_historial.ms_historial.dto.RegistroClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.RegistroClinicoResponseDTO;
import com.servicio_historial.ms_historial.model.HistorialClinico;
import com.servicio_historial.ms_historial.model.RegistroClinico;
import com.servicio_historial.ms_historial.repository.HistorialClinicoRepository;
import com.servicio_historial.ms_historial.repository.RegistroClinicoRepository;
import com.servicio_historial.ms_historial.service.RegistroClinicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroClinicoServiceImpl implements RegistroClinicoService {

    private final RegistroClinicoRepository registroRepository;
    private final HistorialClinicoRepository historialRepository;

    @Override
    public List<RegistroClinicoResponseDTO> listarTodos() {
        return registroRepository.findAll().stream()
                .map(RegistroClinicoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RegistroClinicoResponseDTO> buscarPorId(Long id) {
        return registroRepository.findById(id)
                .map(RegistroClinicoResponseDTO::fromEntity);
    }

    @Override
    public RegistroClinicoResponseDTO guardar(RegistroClinicoRequestDTO dto) {
        HistorialClinico historial = historialRepository.findById(dto.getIdHistorial())
                .orElseThrow(() -> new RuntimeException("Error de negocio: El Historial Clínico con ID " + dto.getIdHistorial() + " no existe."));

        RegistroClinico registro = new RegistroClinico();
        registro.setPeso(dto.getPeso());
        registro.setPresionArterial(dto.getPresionArterial());
        registro.setTemperatura(dto.getTemperatura());
        registro.setObservaciones(dto.getObservaciones());
        
         registro.setHistorialClinico(historial);

        RegistroClinico guardado = registroRepository.save(registro);
        return RegistroClinicoResponseDTO.fromEntity(guardado);
    }

    @Override
    public void eliminar(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("El registro clínico con ID " + id + " no existe.");
        }
        registroRepository.deleteById(id);
    }
}
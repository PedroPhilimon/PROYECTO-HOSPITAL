package com.servicio_medicos.ms_medicos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.servicio_medicos.ms_medicos.dto.MedicoDTO;

@Service
public class MedicoService {

    public List<MedicoDTO> findAll() {
        return null;
    }

    public List<MedicoDTO> findByEspecialidadId(Long id) {
        return null;
    }

    public MedicoDTO findById(Long id) {
    return null; // Por ahora retornamos null para que deje de marcar error
}
}

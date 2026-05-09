package com.servicio_citamedica.ms_citamedica.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio_citamedica.ms_citamedica.model.CitaMedica;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
    List<CitaMedica> findByPacienteId(Long pacienteId);

    List<CitaMedica> findByMedicoId(Long medicoId);

    List<CitaMedica> findByFecha(LocalDate fecha);

    List<CitaMedica> findByEstado(String estado);
}

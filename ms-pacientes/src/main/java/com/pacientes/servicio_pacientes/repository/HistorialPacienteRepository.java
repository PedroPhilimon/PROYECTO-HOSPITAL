package com.pacientes.servicio_pacientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pacientes.servicio_pacientes.model.HistorialPaciente;

@Repository
public interface HistorialPacienteRepository extends JpaRepository<HistorialPaciente, Long> {
    List<HistorialPaciente> findByPacienteId(Long pacienteId);
}

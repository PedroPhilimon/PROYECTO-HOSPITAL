package com.servicio_agenda.ms_agenda.repository;

import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaMedicoRepository extends JpaRepository<AgendaMedico, Long> {
}
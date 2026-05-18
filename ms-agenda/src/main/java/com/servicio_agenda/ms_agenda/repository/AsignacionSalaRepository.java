package com.servicio_agenda.ms_agenda.repository;

import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionSalaRepository extends JpaRepository<AsignacionSala, Long> {
}
package com.servicio_historial.ms_historial.repository;

import com.servicio_historial.ms_historial.model.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Long> {
}
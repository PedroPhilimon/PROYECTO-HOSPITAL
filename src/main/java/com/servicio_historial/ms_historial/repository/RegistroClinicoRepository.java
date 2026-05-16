package com.servicio_historial.ms_historial.repository;

import com.servicio_historial.ms_historial.model.RegistroClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroClinicoRepository extends JpaRepository<RegistroClinico, Long> {
}
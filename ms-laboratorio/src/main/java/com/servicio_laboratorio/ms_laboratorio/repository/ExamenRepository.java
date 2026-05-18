package com.servicio_laboratorio.ms_laboratorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio_laboratorio.ms_laboratorio.model.ExamenLaboratorio;

@Repository
public interface ExamenRepository extends JpaRepository<ExamenLaboratorio, Long> {

}

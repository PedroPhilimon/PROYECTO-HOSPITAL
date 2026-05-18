package com.servicio_laboratorio.ms_laboratorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio_laboratorio.ms_laboratorio.model.OrdenLaboratorio;

@Repository
public interface OrdenRepository extends JpaRepository<OrdenLaboratorio, Long> {

}

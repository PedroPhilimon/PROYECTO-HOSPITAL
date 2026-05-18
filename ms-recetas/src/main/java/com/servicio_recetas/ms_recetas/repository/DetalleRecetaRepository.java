package com.servicio_recetas.ms_recetas.repository;

import com.servicio_recetas.ms_recetas.model.DetalleReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
}
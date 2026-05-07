package com.servicio_medicos.ms_medicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio_medicos.ms_medicos.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{

}

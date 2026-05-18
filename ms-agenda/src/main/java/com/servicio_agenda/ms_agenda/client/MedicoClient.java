package com.servicio_agenda.ms_agenda.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-medicos", url = "http://localhost:8081/api/medicos") 
public interface MedicoClient {

@GetMapping("/{id}")
ResponseEntity<Object> obtenerMedicoPorId(@PathVariable("id") Long id);
}
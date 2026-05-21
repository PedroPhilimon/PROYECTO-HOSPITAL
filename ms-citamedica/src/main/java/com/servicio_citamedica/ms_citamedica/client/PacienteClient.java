package com.servicio_citamedica.ms_citamedica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pacientes", url = "http://localhost:8090/api/pacientes")
public interface PacienteClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id); 
}
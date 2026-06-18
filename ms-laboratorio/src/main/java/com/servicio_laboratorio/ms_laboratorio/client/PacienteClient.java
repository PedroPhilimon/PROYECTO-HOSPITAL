package com.servicio_laboratorio.ms_laboratorio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pacientes", url = "http://hospital-ms-pacientes:8080/api/pacientes")
public interface PacienteClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

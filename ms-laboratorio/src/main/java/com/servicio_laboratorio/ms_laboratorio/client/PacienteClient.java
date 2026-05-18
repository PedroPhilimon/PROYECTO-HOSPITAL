package com.servicio_laboratorio.ms_laboratorio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pacientes", url = "http:localhost:8080")
public interface PacienteClient {

    @GetMapping("/api/pacientes/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

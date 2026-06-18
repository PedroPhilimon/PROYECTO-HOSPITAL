package com.servicio_laboratorio.ms_laboratorio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-medicos", url = "http://hospital-ms-medicos:8081/api/medicos")
public interface MedicoClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

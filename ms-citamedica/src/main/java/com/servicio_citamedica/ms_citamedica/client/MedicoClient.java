package com.servicio_citamedica.ms_citamedica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-medicos" , url = "http://ms-medicos:8080/api/medicos")
public interface MedicoClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}
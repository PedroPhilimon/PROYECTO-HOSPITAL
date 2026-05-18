package com.servicio_laboratorio.ms_laboratorio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-medicos", url = "http:localhost:8081")
public interface MedicoClient {

    @GetMapping("/api/consultas/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

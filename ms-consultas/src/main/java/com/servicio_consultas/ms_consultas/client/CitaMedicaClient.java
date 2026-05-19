package com.servicio_consultas.ms_consultas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-citamedica", url = "http://localhost:8082")
public interface CitaMedicaClient {
    
    @GetMapping("/api/citas/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

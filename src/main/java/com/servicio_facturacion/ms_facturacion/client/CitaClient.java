package com.servicio_facturacion.ms_facturacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-citamedica", url = "http://localhost:8082")
public interface CitaClient {

    @GetMapping("/api/citamedica/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

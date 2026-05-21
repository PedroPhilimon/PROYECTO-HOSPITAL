package com.servicio_facturacion.ms_facturacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-citamedica", url = "http://localhost:8082/api/citas")
public interface CitaClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

package com.servicio_inventario.ms_inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-citamedica")
public interface ProductoClient {

    @GetMapping("api/citas/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

package com.servicio_laboratorio.ms_laboratorio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-consultas", url = "http:localhost:8084")
public interface ConsultaClient {

    @GetMapping("/api/consultas/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

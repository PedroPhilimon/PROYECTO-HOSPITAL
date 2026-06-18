package com.servicio_laboratorio.ms_laboratorio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-consultas", url = "http://hospital-ms-consultas:8087/api/consultas")
public interface ConsultaClient {

    @GetMapping("/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}

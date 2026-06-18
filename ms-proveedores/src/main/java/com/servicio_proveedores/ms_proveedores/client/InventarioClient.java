package com.servicio_proveedores.ms_proveedores.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-inventario")
public interface InventarioClient {

    @GetMapping("/api/productos/{id}")
    ResponseEntity<Object> buscarItemPorId(@PathVariable("id") Long idItemInventario);

    @GetMapping("/api/productos/{id}/validar-stock")
    ResponseEntity<Boolean> validarStock(@PathVariable("id") Long idItemInventario, @RequestParam("cantidad") Integer cantidad);
}
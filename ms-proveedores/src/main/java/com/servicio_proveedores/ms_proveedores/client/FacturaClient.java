package com.servicio_proveedores.ms_proveedores.client;

import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-facturacion", url = "http://localhost:8089/api/facturas")
public interface FacturaClient {


    @PostMapping("/generar-desde-orden")
    ResponseEntity<Object> generarFacturaDeOrden(@RequestBody OrdenCompraResponseDTO ordenCompraDto);
}
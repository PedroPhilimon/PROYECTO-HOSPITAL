package com.servicio_proveedores.ms_proveedores.service;

import java.util.List;
import java.util.Optional;

import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;

public interface OrdenCompraService {

    List<OrdenCompra> listarTodas();

    Optional<OrdenCompra> buscarPorId(Long id);

    OrdenCompra guardar(OrdenCompra orden);

    
} 
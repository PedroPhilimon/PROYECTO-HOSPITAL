package com.servicio_proveedores.ms_proveedores.service;

import java.util.List;
import java.util.Optional;

import com.servicio_proveedores.ms_proveedores.model.Proveedor;

public interface ProveedorService {

    List<Proveedor> listarTodos();

    Optional<Proveedor> buscarPorId(Long id);

    Proveedor guardar(Proveedor proveedor);

    void eliminar(Long id);

} 
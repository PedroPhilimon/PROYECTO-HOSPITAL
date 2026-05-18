package com.servicio_proveedores.ms_proveedores.service;

import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import com.servicio_proveedores.ms_proveedores.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository repository;

    public List<OrdenCompra> listarTodas() {
        return repository.findAll();
    }

    public Optional<OrdenCompra> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public OrdenCompra guardar(OrdenCompra orden) {
        return repository.save(orden);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
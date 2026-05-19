package com.servicio_proveedores.ms_proveedores.service.impl;

import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.repository.ProveedorRepository;
import com.servicio_proveedores.ms_proveedores.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorServiceimpl implements ProveedorService {

    private final ProveedorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> listarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Proveedor guardar(Proveedor proveedor) {
        return repository.save(proveedor);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
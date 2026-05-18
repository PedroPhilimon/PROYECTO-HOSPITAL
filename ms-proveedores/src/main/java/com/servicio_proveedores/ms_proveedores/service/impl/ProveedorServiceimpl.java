package com.servicio_proveedores.ms_proveedores.service.impl;

import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl {

    private final ProveedorRepository repository;

    /
     
/@Transactional(readOnly = true)
  public List<Proveedor> listarTodos() {
      return repository.findAll();}

    /
     
@Transactional(readOnly = true)
  public Optional<Proveedor> buscarPorId(Long id) {
      return repository.findById(id);}

    /
     
@Transactional
  public Proveedor guardar(Proveedor proveedor) {
      return repository.save(proveedor);}

    /
     
@Transactional
public void eliminar(Long id) {
    repository.deleteById(id);}
}
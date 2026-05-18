package com.servicio_proveedores.ms_proveedores.repository;

import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
}
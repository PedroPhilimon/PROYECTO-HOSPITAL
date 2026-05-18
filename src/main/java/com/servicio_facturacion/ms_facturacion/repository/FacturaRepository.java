package com.servicio_facturacion.ms_facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio_facturacion.ms_facturacion.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

}

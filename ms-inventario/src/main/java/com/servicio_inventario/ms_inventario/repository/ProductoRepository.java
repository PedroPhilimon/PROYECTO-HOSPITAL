package com.servicio_inventario.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio_inventario.ms_inventario.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}

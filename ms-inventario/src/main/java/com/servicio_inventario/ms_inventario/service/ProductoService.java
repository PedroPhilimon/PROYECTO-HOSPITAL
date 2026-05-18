package com.servicio_inventario.ms_inventario.service;

import java.util.List;

import com.servicio_inventario.ms_inventario.dto.ProductoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.ProductoResponseDTO;

public interface ProductoService {

    List<ProductoResponseDTO> findAll();

    ProductoResponseDTO findByDto(Long id);

    ProductoResponseDTO create(ProductoRequestDTO dto);

    ProductoResponseDTO update(Long id, ProductoRequestDTO dto);

    void delete(Long id);

}

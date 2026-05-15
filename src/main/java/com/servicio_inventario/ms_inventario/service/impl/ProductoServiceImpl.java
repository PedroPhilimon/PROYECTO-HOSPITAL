package com.servicio_inventario.ms_inventario.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.servicio_inventario.ms_inventario.dto.ProductoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.ProductoResponseDTO;
import com.servicio_inventario.ms_inventario.model.Producto;
import com.servicio_inventario.ms_inventario.repository.ProductoRepository;
import com.servicio_inventario.ms_inventario.service.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO findByDto(Long id) {
        return productoRepository.findById(id)
                .map(ProductoResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con ID: " + id));
    }

    @Override
    public ProductoResponseDTO create(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        
        producto.setId(dto.getId()); 
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setFechaVencimiento(dto.getFechaVencimiento());

        Producto guardarProducto = productoRepository.save(producto);

        return ProductoResponseDTO.fromEntity(guardarProducto);
    }

    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Producto no encontrado con ID: " + id));

        producto.setId(dto.getId()); 
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setFechaVencimiento(dto.getFechaVencimiento());

       
        Producto actualizarProducto = productoRepository.save(producto);

        
        return ProductoResponseDTO.fromEntity(actualizarProducto);
    }

    @Override
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: producto no encontrado con ID: " + id);
        }   
    
        productoRepository.deleteById(id);
    }
}

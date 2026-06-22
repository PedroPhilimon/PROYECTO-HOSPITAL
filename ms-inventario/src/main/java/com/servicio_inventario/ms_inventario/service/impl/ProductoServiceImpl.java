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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoResponseDTO> findAll() {
        log.info("Obteniendo todos los productos registrados en el sistema...");
        return productoRepository.findAll()
                .stream()
                .map(ProductoResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO findByDto(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productoRepository.findById(id)
                .map(ProductoResponseDTO::fromEntity)
                .orElseThrow(() -> {
                    log.error("No se encontró el producto con ID: {}", id);
                    return new RuntimeException("No se encontró el producto con ID: " + id);
                });
    }

    @Override
    public ProductoResponseDTO create(ProductoRequestDTO dto) {
        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setFechaVencimiento(dto.getFechaVencimiento());

        Producto guardarProducto = productoRepository.save(producto);
        log.info("Producto creado exitosamente con ID: {}", guardarProducto.getId());

        return ProductoResponseDTO.fromEntity(guardarProducto);
    }

    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        log.info("Actualizando producto con ID: {}", id);
        log.info("Creando nuevo producto con nombre: '{}'", dto.getNombre());
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: No se encontró el producto con ID: {}", id);
                    return new RuntimeException("No se puede actualizar: Producto no encontrado con ID: " + id);
                });

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
        log.info("Intentando eliminar producto con ID: {}", id);
        if (!productoRepository.existsById(id)) {
            log.error("Error al eliminar: No se encontró el producto con ID: {}", id);
            throw new RuntimeException("No se puede eliminar: producto no encontrado con ID: " + id);
        }   
    
        productoRepository.deleteById(id);
        log.info("Producto con ID: {} eliminado exitosamente", id);
    }
}

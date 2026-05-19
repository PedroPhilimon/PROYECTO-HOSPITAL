package com.servicio_inventario.ms_inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_inventario.ms_inventario.dto.ProductoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.ProductoResponseDTO;
import com.servicio_inventario.ms_inventario.service.ProductoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/productos")
public class ProductoController {
    
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        List<ProductoResponseDTO> citasMedicas = productoService.findAll();
        return ResponseEntity.ok(citasMedicas);
    }


    @GetMapping("/{id}/validar-stock")
    public ResponseEntity<Boolean> validarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        boolean tieneStock = true; 
                
        return ResponseEntity.ok(tieneStock);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByDto(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO crearProducto = productoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO actualizarProducto = productoService.update(id, dto);
        return ResponseEntity.ok(actualizarProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

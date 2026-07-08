package com.servicio_inventario.ms_inventario.service;

import com.servicio_inventario.ms_inventario.dto.ProductoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.ProductoResponseDTO;
import com.servicio_inventario.ms_inventario.model.Producto;
import com.servicio_inventario.ms_inventario.repository.ProductoRepository;
import com.servicio_inventario.ms_inventario.service.impl.ProductoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private ProductoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Paracetamol 500mg");
        producto.setStock(100);
        producto.setPrecio(1500);
        producto.setCategoria("Analgésicos");
        producto.setFechaVencimiento(LocalDate.now().plusYears(2));

        requestDTO = ProductoRequestDTO.builder()
                .nombre("Paracetamol 500mg")
                .stock(100)
                .precio(1500)
                .categoria("Analgésicos")
                .fechaVencimiento(LocalDate.now().plusYears(2))
                .build();
    }

    @Test
    void findAll_Success() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<ProductoResponseDTO> result = productoService.findAll();
        assertEquals(1, result.size());
        assertEquals("Paracetamol 500mg", result.get(0).getNombre());
    }

    @Test
    void findByDto_Success() {
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        ProductoResponseDTO result = productoService.findByDto(1L);
        assertNotNull(result);
        assertEquals(100, result.getStock());
    }

    @Test
    void findByDto_NotFound_ThrowsException() {
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productoService.findByDto(99L));
        assertTrue(exception.getMessage().contains("No se encontró el producto con ID:"));
    }

    @Test
    void create_Success() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        ProductoResponseDTO result = productoService.create(requestDTO);
        assertNotNull(result);
        assertEquals("Paracetamol 500mg", result.getNombre());
    }

    @Test
    void update_Success() {
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        
        ProductoResponseDTO result = productoService.update(1L, requestDTO);
        
        assertNotNull(result);
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void delete_Success() {
        when(productoRepository.existsById(anyLong())).thenReturn(true);
        productoService.delete(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
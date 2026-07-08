package com.servicio_inventario.ms_inventario.service;

import com.servicio_inventario.ms_inventario.client.CitaClient;
import com.servicio_inventario.ms_inventario.dto.MovimientoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.MovimientoResponseDTO;
import com.servicio_inventario.ms_inventario.model.MovimientoInventario;
import com.servicio_inventario.ms_inventario.model.Producto;
import com.servicio_inventario.ms_inventario.repository.MovimientoRepository;
import com.servicio_inventario.ms_inventario.repository.ProductoRepository;
import com.servicio_inventario.ms_inventario.service.impl.MovimientoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceImplTest {

    @Mock
    private MovimientoRepository movimientoRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CitaClient citaClient;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Producto producto;
    private MovimientoInventario movimiento;
    private MovimientoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Jeringas");
        producto.setStock(50);

        movimiento = new MovimientoInventario();
        movimiento.setId(10L);
        movimiento.setProducto(producto);
        movimiento.setCantidad(20);
        movimiento.setTipoMovimiento("ENTRADA");
        movimiento.setFecha(LocalDateTime.now());

        requestDTO = new MovimientoRequestDTO();
        requestDTO.setProductoId(1L);
        requestDTO.setCantidad(20);
        requestDTO.setTipoMovimiento("ENTRADA");
    }

    @Test
    void save_Success_Entrada_AumentaStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(movimientoRepository.save(any(MovimientoInventario.class))).thenReturn(movimiento);

        MovimientoResponseDTO result = movimientoService.save(requestDTO);

        assertNotNull(result);
        assertEquals(70, producto.getStock()); 
        verify(citaClient, never()).buscarPorId(anyLong());
        verify(productoRepository, times(1)).save(producto);
        verify(movimientoRepository, times(1)).save(any(MovimientoInventario.class));
    }

    @Test
    void save_Success_SalidaConCita_DisminuyeStock() {
        requestDTO.setTipoMovimiento("SALIDA");
        requestDTO.setCitaId(99L);
        movimiento.setTipoMovimiento("SALIDA");
        movimiento.setCitaId(99L);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(movimientoRepository.save(any(MovimientoInventario.class))).thenReturn(movimiento);

        MovimientoResponseDTO result = movimientoService.save(requestDTO);

        assertNotNull(result);
        assertEquals(30, producto.getStock());
        verify(citaClient, times(1)).buscarPorId(99L); // Como hay citaId, se debió verificar
    }

    @Test
    void save_Salida_StockInsuficiente_ThrowsException() {
        requestDTO.setTipoMovimiento("SALIDA");
        requestDTO.setCantidad(100);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoService.save(requestDTO);
        });

        assertEquals("Stock insuficiente", exception.getMessage());
        assertEquals(50, producto.getStock());
        verify(movimientoRepository, never()).save(any(MovimientoInventario.class));
    }

    @Test
    void save_CitaInvalida_ThrowsException() {
        requestDTO.setCitaId(99L);
        doThrow(new RuntimeException("Cita no encontrada")).when(citaClient).buscarPorId(99L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoService.save(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error La cita con ID 99 no existe."));
        verify(productoRepository, never()).findById(anyLong());
    }

    @Test
    void save_ProductoNotFound_ThrowsException() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoService.save(requestDTO);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void delete_Success() {
        when(movimientoRepository.existsById(anyLong())).thenReturn(true);
        movimientoService.delete(1L);
        verify(movimientoRepository, times(1)).deleteById(1L);
    }
}
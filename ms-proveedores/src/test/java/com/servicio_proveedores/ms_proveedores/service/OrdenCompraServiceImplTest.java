package com.servicio_proveedores.ms_proveedores.service;

import com.servicio_proveedores.ms_proveedores.client.FacturaClient;
import com.servicio_proveedores.ms_proveedores.client.InventarioClient;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.repository.OrdenCompraRepository;
import com.servicio_proveedores.ms_proveedores.repository.ProveedorRepository;
import com.servicio_proveedores.ms_proveedores.service.impl.OrdenCompraServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenCompraServiceImplTest {

    // Mockeamos 4 dependencias
    @Mock
    private OrdenCompraRepository repository;
    @Mock
    private ProveedorRepository proveedorRepository;
    @Mock
    private InventarioClient inventarioClient;
    @Mock
    private FacturaClient facturaClient;

    @InjectMocks
    private OrdenCompraServiceImpl ordenCompraService;

    private Proveedor proveedor;
    private OrdenCompra ordenCompra;
    private OrdenCompraRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        proveedor.setIdProveedor(1L);

        ordenCompra = new OrdenCompra();
        ordenCompra.setIdOrden(100L);
        ordenCompra.setIdItemInventario(10L);
        ordenCompra.setCantidadPedida(50);
        ordenCompra.setEstado("APROBADA");
        ordenCompra.setProveedor(proveedor);

        requestDTO = OrdenCompraRequestDTO.builder()
                .idProveedor(1L)
                .idItemInventario(10L)
                .cantidadPedida(50)
                .estado("APROBADA")
                .build();
    }

    @Test
    void guardar_Success_GeneraFactura() {
        // Arrange
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        // Simulamos que el microservicio de inventario responde TRUE (hay stock) encapsulado en ResponseEntity
        when(inventarioClient.validarStock(anyLong(), anyInt())).thenReturn(ResponseEntity.ok(true));
        when(repository.save(any(OrdenCompra.class))).thenReturn(ordenCompra);

        // Act
        OrdenCompraResponseDTO result = ordenCompraService.guardar(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("APROBADA", result.getEstado());
        
        // Verificamos que SI se llamó al cliente de facturas porque estaba aprobada
        verify(facturaClient, times(1)).generarFacturaDeOrden(any(OrdenCompraResponseDTO.class));
    }

    @Test
    void guardar_Success_Pendiente_NoGeneraFactura() {
        // Arrange: Cambiamos el estado a pendiente
        requestDTO.setEstado("PENDIENTE");
        ordenCompra.setEstado("PENDIENTE");

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        when(inventarioClient.validarStock(anyLong(), anyInt())).thenReturn(ResponseEntity.ok(true));
        when(repository.save(any(OrdenCompra.class))).thenReturn(ordenCompra);

        // Act
        OrdenCompraResponseDTO result = ordenCompraService.guardar(requestDTO);

        // Assert
        assertEquals("PENDIENTE", result.getEstado());
        // Verificamos que NUNCA se llamó al cliente de facturas
        verify(facturaClient, never()).generarFacturaDeOrden(any());
    }

    @Test
    void guardar_StockInsuficiente_ThrowsException() {
        // Arrange
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        // Simulamos que el inventario responde FALSE (no hay stock)
        when(inventarioClient.validarStock(anyLong(), anyInt())).thenReturn(ResponseEntity.ok(false));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ordenCompraService.guardar(requestDTO);
        });

        assertTrue(exception.getMessage().contains("No hay stock suficiente"));
        // Nunca se intenta guardar ni facturar
        verify(repository, never()).save(any());
        verify(facturaClient, never()).generarFacturaDeOrden(any());
    }

    @Test
    void guardar_IdProveedorNulo_ThrowsIllegalArgumentException() {
        // Arrange
        requestDTO.setIdProveedor(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ordenCompraService.guardar(requestDTO);
        });

        assertEquals("Debe proporcionar un ID de proveedor válido.", exception.getMessage());
        // Validamos que el proceso se detiene inmediatamente
        verify(proveedorRepository, never()).findById(anyLong());
        verify(inventarioClient, never()).validarStock(anyLong(), anyInt());
    }
}
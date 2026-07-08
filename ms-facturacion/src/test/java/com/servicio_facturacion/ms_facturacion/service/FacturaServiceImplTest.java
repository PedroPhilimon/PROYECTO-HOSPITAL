package com.servicio_facturacion.ms_facturacion.service;

import com.servicio_facturacion.ms_facturacion.client.CitaClient;
import com.servicio_facturacion.ms_facturacion.client.PacienteClient;
import com.servicio_facturacion.ms_facturacion.dto.FacturaRequestDTO;
import com.servicio_facturacion.ms_facturacion.dto.FacturaResponseDTO;
import com.servicio_facturacion.ms_facturacion.model.Factura;
import com.servicio_facturacion.ms_facturacion.repository.FacturaRepository;
import com.servicio_facturacion.ms_facturacion.service.impl.FacturaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceImplTest {

    @Mock
    private FacturaRepository facturaRepository;
    
    @Mock
    private PacienteClient pacienteClient;
    
    @Mock
    private CitaClient citaClient;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    private Factura factura;
    private FacturaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        factura = new Factura();
        factura.setId(1L);
        factura.setCitaId(10L);
        factura.setPacienteId(100L);
        factura.setFechaEmision(LocalDateTime.now());
        factura.setMontoSubtotal(50000.0);
        factura.setMontoDescuento(5000.0);
        factura.setMontoTotal(45000.0);
        factura.setEstado("PAGADA");
        factura.setMedioPago("TARJETA");

        requestDTO = FacturaRequestDTO.builder()
                .citaId(10L)
                .pacienteId(100L)
                .fechaEmision(LocalDateTime.now())
                .montoSubtotal(50000.0)
                .montoDescuento(5000.0)
                .montoTotal(45000.0)
                .estado("PAGADA")
                .medioPago("TARJETA")
                .build();
    }

    @Test
    void crearFactura_Success() {
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);

        FacturaResponseDTO result = facturaService.crearFactura(requestDTO);

        assertNotNull(result);
        assertEquals(45000.0, result.getMontoTotal());
        assertEquals("PAGADA", result.getEstado());
        
        verify(pacienteClient, times(1)).buscarPorId(100L);
        verify(citaClient, times(1)).buscarPorId(10L);
        verify(facturaRepository, times(1)).save(any(Factura.class));
    }

    @Test
    void crearFactura_PacienteNoExiste_ThrowsException() {
        when(pacienteClient.buscarPorId(anyLong())).thenThrow(new RuntimeException("Paciente no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facturaService.crearFactura(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error de validación: El paciente con ID 100 no existe."));
        
        verify(citaClient, never()).buscarPorId(anyLong());
        verify(facturaRepository, never()).save(any(Factura.class));
    }

    @Test
    void crearFactura_CitaNoExiste_ThrowsException() {
        when(citaClient.buscarPorId(anyLong())).thenThrow(new RuntimeException("Cita no encontrada"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facturaService.crearFactura(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error de validación: La cita médica con ID 10 no existe."));
        
        verify(pacienteClient, times(1)).buscarPorId(100L);
        verify(facturaRepository, never()).save(any(Factura.class));
    }

    @Test
    void obtenerFacturaPorId_Success() {
        when(facturaRepository.findById(anyLong())).thenReturn(Optional.of(factura));

        FacturaResponseDTO result = facturaService.obtenerFacturaPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TARJETA", result.getMedioPago());
    }

    @Test
    void obtenerFacturaPorId_NotFound_ThrowsException() {
        when(facturaRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facturaService.obtenerFacturaPorId(99L);
        });

        assertEquals("Factura no encontrada con el ID: 99", exception.getMessage());
    }

    @Test
    void obtenerTodasLasFacturas_Success() {
        when(facturaRepository.findAll()).thenReturn(List.of(factura));

        List<FacturaResponseDTO> result = facturaService.obtenerTodasLasFacturas();

        assertEquals(1, result.size());
        assertEquals(45000.0, result.get(0).getMontoTotal());
    }

    @Test
    void eliminarFactura_Success() {
        when(facturaRepository.findById(anyLong())).thenReturn(Optional.of(factura));

        facturaService.eliminarFactura(1L);

        verify(facturaRepository, times(1)).delete(factura);
    }

    @Test
    void eliminarFactura_NotFound_ThrowsException() {
        when(facturaRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facturaService.eliminarFactura(99L);
        });

        assertEquals("No se puede eliminar. Factura no encontrada con el ID: 99", exception.getMessage());
        verify(facturaRepository, never()).delete(any(Factura.class));
    }
}
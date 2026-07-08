package com.servicio_proveedores.ms_proveedores.service;

import com.servicio_proveedores.ms_proveedores.dto.ProveedorRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.ProveedorResponseDTO;
import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.repository.ProveedorRepository;
import com.servicio_proveedores.ms_proveedores.service.impl.ProveedorServiceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceImplTest {

    @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorServiceimpl proveedorService;

    private Proveedor proveedor;
    private ProveedorRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        proveedor.setIdProveedor(1L);
        proveedor.setRut("76.543.210-K");
        proveedor.setNombre("Tech Supplies SpA");

        requestDTO = ProveedorRequestDTO.builder()
                .rut("76.543.210-K")
                .nombre("Tech Supplies SpA")
                .build();
    }

    @Test
    void listarTodos_Success() {
        when(repository.findAll()).thenReturn(List.of(proveedor));
        List<ProveedorResponseDTO> result = proveedorService.listarTodos();
        assertEquals(1, result.size());
        assertEquals("Tech Supplies SpA", result.get(0).getNombre());
    }

    @Test
    void buscarPorId_Success() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(proveedor));
        ProveedorResponseDTO result = proveedorService.buscarPorId(1L);
        assertNotNull(result);
        assertEquals("76.543.210-K", result.getRut());
    }

    @Test
    void buscarPorId_NotFound_ThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> proveedorService.buscarPorId(99L));
        assertTrue(exception.getMessage().contains("Proveedor no encontrado con el ID:"));
    }

    @Test
    void guardar_Success() {
        when(repository.save(any(Proveedor.class))).thenReturn(proveedor);
        ProveedorResponseDTO result = proveedorService.guardar(requestDTO);
        assertNotNull(result);
        assertEquals("Tech Supplies SpA", result.getNombre());
    }
}
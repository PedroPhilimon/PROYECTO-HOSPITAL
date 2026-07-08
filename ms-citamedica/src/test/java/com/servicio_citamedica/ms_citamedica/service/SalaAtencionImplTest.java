package com.servicio_citamedica.ms_citamedica.service;

import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionRequestDTO;
import com.servicio_citamedica.ms_citamedica.dto.SalaAtencionResponseDTO;
import com.servicio_citamedica.ms_citamedica.model.SalaAtencion;
import com.servicio_citamedica.ms_citamedica.repository.SalaAtencionRepository;
import com.servicio_citamedica.ms_citamedica.service.impl.SalaAtencionImpl;

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
class SalaAtencionImplTest {

    @Mock
    private SalaAtencionRepository salaAtencionRepository;

    @InjectMocks
    private SalaAtencionImpl salaAtencionService;

    private SalaAtencion sala;
    private SalaAtencionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        sala = new SalaAtencion();
        sala.setId(1L);
        sala.setNombre("Sala Quirúrgica A");
        sala.setUbicacion("Piso 3");

        requestDTO = SalaAtencionRequestDTO.builder()
                .nombre("Sala Quirúrgica A")
                .ubicacion("Piso 3")
                .build();
    }

    @Test
    void findAll_Success() {
        when(salaAtencionRepository.findAll()).thenReturn(List.of(sala));
        List<SalaAtencionResponseDTO> result = salaAtencionService.findAll();
        assertEquals(1, result.size());
        assertEquals("Sala Quirúrgica A", result.get(0).getNombre());
    }

    @Test
    void findById_Success() {
        when(salaAtencionRepository.findById(anyLong())).thenReturn(Optional.of(sala));
        SalaAtencionResponseDTO result = salaAtencionService.findById(1L);
        assertNotNull(result);
        assertEquals("Piso 3", result.getUbicacion());
    }

    @Test
    void findById_NotFound_ThrowsException() {
        when(salaAtencionRepository.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> salaAtencionService.findById(99L));
        assertEquals("Sala no encontrada con ID: 99", exception.getMessage());
    }

    @Test
    void create_Success() {
        when(salaAtencionRepository.save(any(SalaAtencion.class))).thenReturn(sala);
        SalaAtencionResponseDTO result = salaAtencionService.create(requestDTO);
        assertNotNull(result);
        assertEquals("Sala Quirúrgica A", result.getNombre());
    }

    @Test
    void update_Success() {
        when(salaAtencionRepository.findById(anyLong())).thenReturn(Optional.of(sala));
        when(salaAtencionRepository.save(any(SalaAtencion.class))).thenReturn(sala);
        
        SalaAtencionResponseDTO result = salaAtencionService.update(1L, requestDTO);
        
        assertNotNull(result);
        verify(salaAtencionRepository, times(1)).findById(1L);
        verify(salaAtencionRepository, times(1)).save(any(SalaAtencion.class));
    }

    @Test
    void delete_Success() {
        when(salaAtencionRepository.existsById(anyLong())).thenReturn(true);
        salaAtencionService.delete(1L);
        verify(salaAtencionRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void existsById_Success() {
        when(salaAtencionRepository.existsById(anyLong())).thenReturn(true);
        boolean exists = salaAtencionService.existsById(1L);
        assertTrue(exists);
    }
}
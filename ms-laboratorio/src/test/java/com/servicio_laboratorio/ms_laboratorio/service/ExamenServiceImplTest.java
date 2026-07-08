package com.servicio_laboratorio.ms_laboratorio.service;

import com.servicio_laboratorio.ms_laboratorio.dto.ExamenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.ExamenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.model.ExamenLaboratorio;
import com.servicio_laboratorio.ms_laboratorio.model.OrdenLaboratorio;
import com.servicio_laboratorio.ms_laboratorio.repository.ExamenRepository;
import com.servicio_laboratorio.ms_laboratorio.repository.OrdenRepository;
import com.servicio_laboratorio.ms_laboratorio.service.impl.ExamenServiceImpl;

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
class ExamenServiceImplTest {

    @Mock
    private ExamenRepository examenRepository;

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private ExamenServiceImpl examenService;

    private ExamenLaboratorio examenLaboratorio;
    private OrdenLaboratorio ordenLaboratorio;
    private ExamenRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ordenLaboratorio = new OrdenLaboratorio();
        ordenLaboratorio.setId(10L);

        examenLaboratorio = new ExamenLaboratorio();
        examenLaboratorio.setId(1L);
        examenLaboratorio.setOrdenId(10L);
        examenLaboratorio.setNombreExamen("Hemograma");
        examenLaboratorio.setResultado("Normal");
        examenLaboratorio.setObservacion("Sin alteraciones");
        examenLaboratorio.setOrden(ordenLaboratorio);

        requestDTO = ExamenRequestDTO.builder()
                .ordenId(10L)
                .nombreExamen("Hemograma")
                .resultado("Normal")
                .observacion("Sin alteraciones")
                .build();
    }

    @Test
    void crearExamen_Success() {
        // Arrange
        when(ordenRepository.findById(anyLong())).thenReturn(Optional.of(ordenLaboratorio));
        when(examenRepository.save(any(ExamenLaboratorio.class))).thenReturn(examenLaboratorio);

        // Act
        ExamenResponseDTO result = examenService.crearExamen(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Hemograma", result.getNombreExamen());
        verify(ordenRepository, times(1)).findById(10L);
        verify(examenRepository, times(1)).save(any(ExamenLaboratorio.class));
    }

    @Test
    void crearExamen_OrdenNoExiste_ThrowsException() {
        // Arrange: La orden no existe en la base de datos
        when(ordenRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            examenService.crearExamen(requestDTO);
        });

        assertTrue(exception.getMessage().contains("No se puede crear el examen: La Orden de Laboratorio con ID"));
        // Nunca se debe intentar guardar el examen
        verify(examenRepository, never()).save(any(ExamenLaboratorio.class));
    }

    @Test
    void obtenerExamenPorId_Success() {
        when(examenRepository.findById(1L)).thenReturn(Optional.of(examenLaboratorio));
        ExamenResponseDTO result = examenService.obtenerExamenPorId(1L);
        assertEquals("Hemograma", result.getNombreExamen());
    }

    @Test
    void obtenerExamenesPorOrdenId_Success() {
        // Arrange
        when(ordenRepository.existsById(10L)).thenReturn(true);
        when(examenRepository.findAll()).thenReturn(List.of(examenLaboratorio));

        // Act
        List<ExamenResponseDTO> result = examenService.obtenerExamenesPorOrdenId(10L);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Hemograma", result.get(0).getNombreExamen());
    }

    @Test
    void obtenerExamenesPorOrdenId_OrdenNotFound_ThrowsException() {
        // Arrange
        when(ordenRepository.existsById(10L)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            examenService.obtenerExamenesPorOrdenId(10L);
        });
        
        assertTrue(exception.getMessage().contains("La Orden de Laboratorio con ID 10 no existe."));
    }
}
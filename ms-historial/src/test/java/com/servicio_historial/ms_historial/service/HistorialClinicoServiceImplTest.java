package com.servicio_historial.ms_historial.service;

import com.servicio_historial.ms_historial.client.MedicoClient;
import com.servicio_historial.ms_historial.client.PacienteClient;
import com.servicio_historial.ms_historial.dto.HistorialClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.HistorialClinicoResponseDTO;
import com.servicio_historial.ms_historial.model.HistorialClinico;
import com.servicio_historial.ms_historial.repository.HistorialClinicoRepository;
import com.servicio_historial.ms_historial.service.impl.HistorialClinicoServiceImpl;

import feign.FeignException;
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
class HistorialClinicoServiceImplTest {

    @Mock
    private HistorialClinicoRepository repository;
    @Mock
    private PacienteClient pacienteClient;
    @Mock
    private MedicoClient medicoClient;

    @InjectMocks
    private HistorialClinicoServiceImpl historialService;

    private HistorialClinico historial;
    private HistorialClinicoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        historial = new HistorialClinico();
        historial.setIdHistorial(1L);
        historial.setIdPaciente(100L);
        historial.setIdMedico(200L);
        historial.setDiagnostico("Gripe común");
        historial.setTratamiento("Reposo e hidratación");
        historial.setFechaAtencion(LocalDateTime.now());

        requestDTO = HistorialClinicoRequestDTO.builder()
                .idPaciente(100L)
                .idMedico(200L)
                .diagnostico("Gripe común")
                .tratamiento("Reposo e hidratación")
                .build();
    }

    @Test
    void listarTodos_Success() {
        when(repository.findAll()).thenReturn(List.of(historial));
        List<HistorialClinicoResponseDTO> result = historialService.listarTodos();
        assertEquals(1, result.size());
    }

    @Test
    void buscarPorId_Success() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(historial));
        Optional<HistorialClinicoResponseDTO> result = historialService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals("Gripe común", result.get().getDiagnostico());
    }

    @Test
    void guardar_Success() {
        when(repository.save(any(HistorialClinico.class))).thenReturn(historial);

        HistorialClinicoResponseDTO result = historialService.guardar(requestDTO);

        assertNotNull(result);
        assertEquals("Gripe común", result.getDiagnostico());
        verify(pacienteClient, times(1)).buscarPorId(100L);
        verify(medicoClient, times(1)).buscarPorId(200L);
        verify(repository, times(1)).save(any(HistorialClinico.class));
    }

    @Test
    void guardar_PacienteNotFound_ThrowsException() {
        doThrow(mock(FeignException.NotFound.class)).when(pacienteClient).buscarPorId(anyLong());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            historialService.guardar(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error de negocio: El paciente con ID 100 no existe."));
        verify(medicoClient, never()).buscarPorId(anyLong());
        verify(repository, never()).save(any(HistorialClinico.class));
    }

    @Test
    void guardar_MedicoCommunicationError_ThrowsException() {
        doThrow(mock(FeignException.class)).when(medicoClient).buscarPorId(anyLong());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            historialService.guardar(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error de comunicación: No se pudo verificar el médico."));
        verify(repository, never()).save(any(HistorialClinico.class));
    }

    @Test
    void eliminar_Success() {
        when(repository.existsById(anyLong())).thenReturn(true);
        historialService.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void eliminar_NotFound_ThrowsException() {
        when(repository.existsById(anyLong())).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> historialService.eliminar(99L));
        assertTrue(exception.getMessage().contains("El historial clínico con ID 99 no existe."));
    }
}
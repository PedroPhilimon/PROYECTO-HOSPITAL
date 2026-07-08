package com.servicio_historial.ms_historial.service;

import com.servicio_historial.ms_historial.dto.RegistroClinicoRequestDTO;
import com.servicio_historial.ms_historial.dto.RegistroClinicoResponseDTO;
import com.servicio_historial.ms_historial.model.HistorialClinico;
import com.servicio_historial.ms_historial.model.RegistroClinico;
import com.servicio_historial.ms_historial.repository.HistorialClinicoRepository;
import com.servicio_historial.ms_historial.repository.RegistroClinicoRepository;
import com.servicio_historial.ms_historial.service.impl.RegistroClinicoServiceImpl;

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
class RegistroClinicoServiceImplTest {

    @Mock
    private RegistroClinicoRepository registroRepository;

    @Mock
    private HistorialClinicoRepository historialRepository;

    @InjectMocks
    private RegistroClinicoServiceImpl registroService;

    private RegistroClinico registro;
    private HistorialClinico historial;
    private RegistroClinicoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        historial = new HistorialClinico();
        historial.setIdHistorial(1L);

        registro = new RegistroClinico();
        registro.setId(10L); 
        registro.setPeso("75.5"); 
        registro.setPresionArterial("120/80");
        registro.setTemperatura("36.6"); 
        registro.setObservaciones("Paciente estable");
        registro.setHistorialClinico(historial);

        requestDTO = RegistroClinicoRequestDTO.builder()
                .idHistorial(1L)
                .peso("75.5") 
                .presionArterial("120/80")
                .temperatura("36.6") 
                .observaciones("Paciente estable")
                .build();
    }

    @Test
    void listarTodos_Success() {
        when(registroRepository.findAll()).thenReturn(List.of(registro));
        List<RegistroClinicoResponseDTO> result = registroService.listarTodos();
        assertEquals(1, result.size());
    }

    @Test
    void buscarPorId_Success() {
        when(registroRepository.findById(anyLong())).thenReturn(Optional.of(registro));
        Optional<RegistroClinicoResponseDTO> result = registroService.buscarPorId(10L);
        assertTrue(result.isPresent());
        assertEquals("36.6", result.get().getTemperatura()); 
    }

    @Test
    void guardar_Success() {
        when(historialRepository.findById(anyLong())).thenReturn(Optional.of(historial));
        when(registroRepository.save(any(RegistroClinico.class))).thenReturn(registro);

        RegistroClinicoResponseDTO result = registroService.guardar(requestDTO);

        assertNotNull(result);
        assertEquals("120/80", result.getPresionArterial());
        verify(historialRepository, times(1)).findById(1L);
        verify(registroRepository, times(1)).save(any(RegistroClinico.class));
    }

    @Test
    void guardar_HistorialNotFound_ThrowsException() {
        when(historialRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registroService.guardar(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error de negocio: El Historial Clínico con ID 1 no existe."));
        verify(registroRepository, never()).save(any(RegistroClinico.class));
    }

    @Test
    void eliminar_Success() {
        when(registroRepository.existsById(anyLong())).thenReturn(true);
        registroService.eliminar(10L);
        verify(registroRepository, times(1)).deleteById(10L);
    }

    @Test
    void eliminar_NotFound_ThrowsException() {
        when(registroRepository.existsById(anyLong())).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> registroService.eliminar(99L));
        assertTrue(exception.getMessage().contains("El registro clínico con ID 99 no existe."));
    }
}
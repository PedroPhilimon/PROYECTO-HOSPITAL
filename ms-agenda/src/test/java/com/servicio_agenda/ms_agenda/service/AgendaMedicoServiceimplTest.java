package com.servicio_agenda.ms_agenda.service;

import com.servicio_agenda.ms_agenda.client.MedicoClient;
import com.servicio_agenda.ms_agenda.dto.AgendaMedicoResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.repository.AgendaMedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaMedicoServiceimplTest {

    @Mock
    private AgendaMedicoRepository repository;

    @Mock
    private MedicoClient medicoClient;

    @InjectMocks
    private AgendaMedicoServiceimpl agendaMedicoService;

    private AgendaMedico agendaMock;

    @BeforeEach
    void setUp() {
        agendaMock = new AgendaMedico();
        agendaMock.setIdAgenda(1L);
        agendaMock.setIdMedico(100L);
    }

    @Test
    void listarTodas_Success() {
        when(repository.findAll()).thenReturn(Arrays.asList(agendaMock));

        List<AgendaMedico> resultado = agendaMedicoService.listarTodas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendaMock));

        Optional<AgendaMedico> resultado = agendaMedicoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdAgenda());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void guardar_Success() {
        // Simulamos que el cliente HTTP encuentra al médico
        when(medicoClient.obtenerMedicoPorId(100L))
                .thenReturn(ResponseEntity.ok(new Object())); // Body no nulo y status OK

        when(repository.save(any(AgendaMedico.class))).thenReturn(agendaMock);

        AgendaMedicoResponseDTO resultado = agendaMedicoService.guardar(agendaMock);

        assertNotNull(resultado);
        verify(medicoClient, times(1)).obtenerMedicoPorId(100L);
        verify(repository, times(1)).save(any(AgendaMedico.class));
    }

    @Test
    void guardar_MedicoNoExisteBodyNull_ThrowsException() {
        // Simulamos que el endpoint responde pero sin cuerpo (body null)
        when(medicoClient.obtenerMedicoPorId(100L))
                .thenReturn(ResponseEntity.ok().body(null));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendaMedicoService.guardar(agendaMock);
        });

        assertTrue(exception.getMessage().contains("El médico con ID 100 no existe en el sistema."));
        verify(repository, never()).save(any(AgendaMedico.class));
    }

    @Test
    void guardar_MedicoClientError_ThrowsException() {
        // Simulamos que el microservicio de médicos está caído o arroja excepción
        when(medicoClient.obtenerMedicoPorId(anyLong()))
                .thenThrow(new RuntimeException("Connection refused"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendaMedicoService.guardar(agendaMock);
        });

        assertTrue(exception.getMessage().contains("Error al validar el médico. El microservicio no responde"));
        verify(repository, never()).save(any(AgendaMedico.class));
    }

    @Test
    void eliminar_Success() {
        doNothing().when(repository).deleteById(1L);

        agendaMedicoService.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
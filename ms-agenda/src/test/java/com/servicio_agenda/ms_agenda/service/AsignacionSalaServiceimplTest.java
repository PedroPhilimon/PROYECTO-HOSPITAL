package com.servicio_agenda.ms_agenda.service;

import com.servicio_agenda.ms_agenda.dto.AsignacionSalaRequestDTO;
import com.servicio_agenda.ms_agenda.dto.AsignacionSalaResponseDTO;
import com.servicio_agenda.ms_agenda.model.AgendaMedico;
import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.repository.AgendaMedicoRepository;
import com.servicio_agenda.ms_agenda.repository.AsignacionSalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignacionSalaServiceimplTest {

    @Mock
    private AsignacionSalaRepository asignacionSalaRepository;

    @Mock
    private AgendaMedicoRepository agendaMedicoRepository;

    @InjectMocks
    private AsignacionSalaServiceimpl asignacionSalaService;

    private AsignacionSala asignacionMock;
    private AgendaMedico agendaMock;
    private AsignacionSalaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        agendaMock = new AgendaMedico();
        agendaMock.setIdAgenda(1L);

        asignacionMock = new AsignacionSala();
        asignacionMock.setIdAsignacion(10L);
        asignacionMock.setIdSala(5L);
        asignacionMock.setMotivoBloqueo("Mantenimiento");
        asignacionMock.setAgendaMedico(agendaMock);


        requestDTO = new AsignacionSalaRequestDTO();

        requestDTO.setIdSala(5L);
        requestDTO.setMotivoBloqueo("Mantenimiento");
    }

    @Test
    void listarTodas_Success() {
        when(asignacionSalaRepository.findAll()).thenReturn(Arrays.asList(asignacionMock));

        List<AsignacionSala> resultado = asignacionSalaService.listarTodas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(asignacionSalaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Success() {
        when(asignacionSalaRepository.findById(10L)).thenReturn(Optional.of(asignacionMock));

        Optional<AsignacionSala> resultado = asignacionSalaService.buscarPorId(10L);

        assertTrue(resultado.isPresent());
        assertEquals(10L, resultado.get().getIdAsignacion());
        verify(asignacionSalaRepository, times(1)).findById(10L);
    }

    @Test
    void guardar_Success() {
        when(agendaMedicoRepository.findById(1L)).thenReturn(Optional.of(agendaMock));
        when(asignacionSalaRepository.save(any(AsignacionSala.class))).thenReturn(asignacionMock);

        AsignacionSalaResponseDTO resultado = asignacionSalaService.guardar(requestDTO, 1L);

        assertNotNull(resultado);
        verify(agendaMedicoRepository, times(1)).findById(1L);
        verify(asignacionSalaRepository, times(1)).save(any(AsignacionSala.class));
    }

    @Test
    void guardar_AgendaNotFound_ThrowsException() {
        when(agendaMedicoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asignacionSalaService.guardar(requestDTO, 1L);
        });

        assertEquals("La Agenda Médica con ID 1 no existe.", exception.getMessage());
        verify(asignacionSalaRepository, never()).save(any(AsignacionSala.class));
    }

    @Test
    void eliminar_Success() {
        doNothing().when(asignacionSalaRepository).deleteById(10L);

        asignacionSalaService.eliminar(10L);

        verify(asignacionSalaRepository, times(1)).deleteById(10L);
    }
}
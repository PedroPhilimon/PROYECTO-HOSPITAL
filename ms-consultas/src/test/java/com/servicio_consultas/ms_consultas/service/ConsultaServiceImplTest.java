package com.servicio_consultas.ms_consultas.service;

import com.servicio_consultas.ms_consultas.client.CitaMedicaClient;
import com.servicio_consultas.ms_consultas.client.MedicoClient;
import com.servicio_consultas.ms_consultas.client.PacienteClient;
import com.servicio_consultas.ms_consultas.dto.ConsultaRequestDTO;
import com.servicio_consultas.ms_consultas.model.Consulta;
import com.servicio_consultas.ms_consultas.repository.ConsultaRepository;
import com.servicio_consultas.ms_consultas.service.impl.ConsultaServiceImpl;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceImplTest {

    @Mock
    private ConsultaRepository consultaRepository;
    
    @Mock
    private PacienteClient pacienteClient;
    
    @Mock
    private MedicoClient medicoClient;
    
    @Mock
    private CitaMedicaClient citaMedicaClient;

    @InjectMocks
    private ConsultaServiceImpl consultaService;

    private Consulta consulta;
    private ConsultaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        consulta = new Consulta();
        consulta.setId(1L);
        consulta.setPacienteId(10L);
        consulta.setMedicoId(20L);
        consulta.setCitaId(30L);
        consulta.setFecha(LocalDate.now());
        consulta.setMotivoConsulta("Dolor de cabeza");
        consulta.setDiagnostico("Migraña");
        consulta.setObservaciones("Tomar paracetamol");

        requestDTO = new ConsultaRequestDTO();
        requestDTO.setPacienteId(10L);
        requestDTO.setMedicoId(20L);
        requestDTO.setCitaId(30L);
        requestDTO.setFecha(LocalDate.now());
        requestDTO.setMotivoConsulta("Dolor de cabeza");
        requestDTO.setDiagnostico("Migraña");
        requestDTO.setObservaciones("Tomar paracetamol");
    }

    @Test
    void registrarConsulta_Success() {
        // Arrange: Simulamos que se guarda correctamente y los clientes Feign pasan sin arrojar error
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        Consulta result = consultaService.registrarConsulta(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Dolor de cabeza", result.getMotivoConsulta());
        assertEquals("Migraña", result.getDiagnostico());
        
        // Verificamos que se llamó a las 3 validaciones y luego se guardó
        verify(pacienteClient, times(1)).buscarPorId(10L);
        verify(medicoClient, times(1)).buscarPorId(20L);
        verify(citaMedicaClient, times(1)).buscarPorId(30L);
        verify(consultaRepository, times(1)).save(any(Consulta.class));
    }

    @Test
    void registrarConsulta_PacienteNotFound_ThrowsIllegalArgumentException() {
        // Arrange: Simulamos que el Paciente no existe (FeignException.NotFound)
        doThrow(mock(FeignException.NotFound.class)).when(pacienteClient).buscarPorId(anyLong());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consultaService.registrarConsulta(requestDTO);
        });

        assertEquals("Error: El paciente con ID 10 no existe.", exception.getMessage());
        
        // Verificamos cortocircuito: no avanza al médico, ni a la cita, ni guarda en BD
        verify(medicoClient, never()).buscarPorId(anyLong());
        verify(citaMedicaClient, never()).buscarPorId(anyLong());
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void registrarConsulta_MedicoErrorComunicacion_ThrowsRuntimeException() {
        // Arrange: Paciente pasa bien, pero Médico falla por error de comunicación (FeignException general)
        doThrow(mock(FeignException.class)).when(medicoClient).buscarPorId(anyLong());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            consultaService.registrarConsulta(requestDTO);
        });

        assertEquals("Error de comunicación con el servicio de médicos.", exception.getMessage());
        
        // Validamos que se llamó a paciente, pero NO a la cita, ni al repositorio
        verify(pacienteClient, times(1)).buscarPorId(10L);
        verify(citaMedicaClient, never()).buscarPorId(anyLong());
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void registrarConsulta_CitaNotFound_ThrowsIllegalArgumentException() {
        // Arrange: Paciente y médico pasan, pero la Cita no existe
        doThrow(mock(FeignException.NotFound.class)).when(citaMedicaClient).buscarPorId(anyLong());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consultaService.registrarConsulta(requestDTO);
        });

        assertEquals("Error: La cita médica con ID 30 no existe.", exception.getMessage());
        verify(pacienteClient, times(1)).buscarPorId(10L);
        verify(medicoClient, times(1)).buscarPorId(20L);
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void buscarPorId_Success() {
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.of(consulta));
        
        Consulta result = consultaService.buscarPorId(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Migraña", result.getDiagnostico());
    }

    @Test
    void buscarPorId_NotFound_ThrowsIllegalArgumentException() {
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consultaService.buscarPorId(99L);
        });

        assertEquals("Error: La consulta con ID 99 no existe.", exception.getMessage());
    }

    @Test
    void eliminarConsulta_Success() {
        // Arrange: El método busca primero por ID y luego pasa la entidad al delete()
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.of(consulta));

        // Act
        consultaService.eliminarConsulta(1L);

        // Assert
        verify(consultaRepository, times(1)).delete(consulta);
    }

    @Test
    void eliminarConsulta_NotFound_ThrowsIllegalArgumentException() {
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consultaService.eliminarConsulta(99L);
        });

        assertEquals("Error: No se puede eliminar. La consulta con ID 99 no existe.", exception.getMessage());
        verify(consultaRepository, never()).delete(any(Consulta.class));
    }
}
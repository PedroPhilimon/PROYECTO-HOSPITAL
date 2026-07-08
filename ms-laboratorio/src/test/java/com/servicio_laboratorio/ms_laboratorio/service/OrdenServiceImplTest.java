package com.servicio_laboratorio.ms_laboratorio.service;

import com.servicio_laboratorio.ms_laboratorio.client.ConsultaClient;
import com.servicio_laboratorio.ms_laboratorio.client.MedicoClient;
import com.servicio_laboratorio.ms_laboratorio.client.PacienteClient;
import com.servicio_laboratorio.ms_laboratorio.dto.OrdenRequestDTO;
import com.servicio_laboratorio.ms_laboratorio.dto.OrdenResponseDTO;
import com.servicio_laboratorio.ms_laboratorio.model.OrdenLaboratorio;
import com.servicio_laboratorio.ms_laboratorio.repository.OrdenRepository;
import com.servicio_laboratorio.ms_laboratorio.service.impl.OrdenServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenServiceImplTest {

    @Mock
    private OrdenRepository ordenRepository;
    @Mock
    private PacienteClient pacienteClient;
    @Mock
    private MedicoClient medicoClient;
    @Mock
    private ConsultaClient consultaClient;

    @InjectMocks
    private OrdenServiceImpl ordenService;

    private OrdenLaboratorio ordenLaboratorio;
    private OrdenRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ordenLaboratorio = new OrdenLaboratorio();
        ordenLaboratorio.setId(1L);
        ordenLaboratorio.setConsultaId(10L);
        ordenLaboratorio.setPacienteId(100L);
        ordenLaboratorio.setMedicoId(200L);
        ordenLaboratorio.setFecha(LocalDate.now());

        requestDTO = OrdenRequestDTO.builder()
                .consultaId(10L)
                .pacienteId(100L)
                .medicoId(200L)
                .fecha(LocalDate.now())
                .build();
    }

    @Test
    void crearOrden_Success() {
        // Arrange: Simulamos que al guardar la orden retorna la entidad (Los Feign clients por defecto al no fallar pasan de largo en el try-catch)
        when(ordenRepository.save(any(OrdenLaboratorio.class))).thenReturn(ordenLaboratorio);

        // Act
        OrdenResponseDTO result = ordenService.crearOrden(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getConsultaId());
        
        // Verificamos que se consultaron los 3 microservicios externos y se guardó en BD
        verify(pacienteClient, times(1)).buscarPorId(100L);
        verify(medicoClient, times(1)).buscarPorId(200L);
        verify(consultaClient, times(1)).buscarPorId(10L);
        verify(ordenRepository, times(1)).save(any(OrdenLaboratorio.class));
    }

    @Test
    void crearOrden_PacienteFails_ThrowsException() {
        // Arrange: Simulamos que el microservicio de Paciente está caído o lanza un error
        when(pacienteClient.buscarPorId(anyLong())).thenThrow(new RuntimeException("Error simulado"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ordenService.crearOrden(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error al validar el Paciente:"));
        
        // Verificamos que si falla el paciente, el proceso se corta y NO llama a los otros clientes ni guarda en BD
        verify(medicoClient, never()).buscarPorId(anyLong());
        verify(consultaClient, never()).buscarPorId(anyLong());
        verify(ordenRepository, never()).save(any(OrdenLaboratorio.class));
    }

    @Test
    void obtenerOrdenPorId_Success() {
        // Arrange
        when(ordenRepository.findById(1L)).thenReturn(Optional.of(ordenLaboratorio));

        // Act
        OrdenResponseDTO result = ordenService.obtenerOrdenPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void obtenerTodasLasOrdenes_Success() {
        // Arrange
        when(ordenRepository.findAll()).thenReturn(List.of(ordenLaboratorio));

        // Act
        List<OrdenResponseDTO> result = ordenService.obtenerTodasLasOrdenes();

        // Assert
        assertEquals(1, result.size());
    }
}
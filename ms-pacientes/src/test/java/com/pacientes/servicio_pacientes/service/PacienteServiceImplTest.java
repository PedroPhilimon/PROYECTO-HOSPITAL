package com.pacientes.servicio_pacientes.service;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.dto.PacienteResponseDTO;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.repository.PacienteRepository;
import com.pacientes.servicio_pacientes.service.impl.PacienteServiceImpl;
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
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente pacienteEntity;
    private PacienteRequestDTO pacienteRequestDTO;

    @BeforeEach
    void setUp() {
        pacienteEntity = new Paciente();
        pacienteEntity.setId(1L);
        pacienteEntity.setRun("12.345.678-9");
        pacienteEntity.setNombre("Juan");
        pacienteEntity.setApellido("Pérez");
        pacienteEntity.setFechaNacimiento(LocalDate.of(1990, 5, 14));
        pacienteEntity.setPrevision("Fonasa");

        pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setRun("12.345.678-9");
        pacienteRequestDTO.setNombre("Juan");
        pacienteRequestDTO.setApellido("Pérez");
        pacienteRequestDTO.setFechaNacimiento(LocalDate.of(1990, 5, 14));
        pacienteRequestDTO.setPrevision("Fonasa");
    }

    @Test
    void create_Success() {
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEntity);


        PacienteResponseDTO result = pacienteService.create(pacienteRequestDTO);

        assertNotNull(result);
        assertEquals("12.345.678-9", result.getRun());
        assertEquals("Juan", result.getNombre());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void findByDto_Success() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(pacienteEntity));

        PacienteResponseDTO result = pacienteService.findByDto(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellido());
        assertEquals(1L, result.getId());
    }

    @Test
    void findByDto_NotFound_ThrowsException() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.findByDto(99L);
        });

        assertTrue(exception.getMessage().contains("No se encontró el paciente con ID:"));
    }

    @Test
    void update_Success() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(pacienteEntity));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEntity);

        PacienteResponseDTO result = pacienteService.update(1L, pacienteRequestDTO);

        assertNotNull(result);
        assertEquals("Fonasa", result.getPrevision());
        verify(pacienteRepository, times(1)).findById(1L);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void delete_Success() {
        when(pacienteRepository.existsById(anyLong())).thenReturn(true);

        pacienteService.delete(1L);

        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_NotFound_ThrowsException() {
        when(pacienteRepository.existsById(anyLong())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.delete(99L);
        });

        assertTrue(exception.getMessage().contains("No se puede eliminar: Paciente no encontrado con ID:"));
        
        verify(pacienteRepository, never()).deleteById(anyLong());
    }
}
package com.servicio_medicos.ms_medicos.service;

import com.servicio_medicos.ms_medicos.Repository.EspecialidadRepository;
import com.servicio_medicos.ms_medicos.Repository.MedicoRepository;
import com.servicio_medicos.ms_medicos.Service.impl.MedicoServiceImpl;
import com.servicio_medicos.ms_medicos.dto.MedicoRequestDTO;
import com.servicio_medicos.ms_medicos.dto.MedicoResponseDTO;
import com.servicio_medicos.ms_medicos.model.Especialidad;
import com.servicio_medicos.ms_medicos.model.Medico;
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
class MedicoServiceImplTest {

    // 1. Mockeamos AMBOS repositorios que usa tu servicio
    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EspecialidadRepository especialidadRepository;

    // 2. Inyectamos los mocks en el servicio real
    @InjectMocks
    private MedicoServiceImpl medicoService;

    private Medico medicoEntity;
    private Especialidad especialidadEntity;
    private MedicoRequestDTO medicoRequestDTO;

    @BeforeEach
    void setUp() {
        especialidadEntity = new Especialidad();


        medicoEntity = new Medico();
        medicoEntity.setId(1L);
        medicoEntity.setNombre("Gregory");
        medicoEntity.setApellido("House");
        medicoEntity.setEmail("ghouse@hospital.com");
        medicoEntity.setNumero("123456789");
        medicoEntity.setEspecialidad(especialidadEntity);

        // Preparamos el DTO de entrada
        medicoRequestDTO = new MedicoRequestDTO();
        medicoRequestDTO.setEspecialidadId(1L);
        medicoRequestDTO.setNombre("Gregory");
        medicoRequestDTO.setApellido("House");
        medicoRequestDTO.setEmail("ghouse@hospital.com");
        medicoRequestDTO.setNumero("123456789");
    }

    @Test
    void create_Success() {
        when(especialidadRepository.findById(anyLong())).thenReturn(Optional.of(especialidadEntity));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoEntity);

        MedicoResponseDTO result = medicoService.create(medicoRequestDTO);

        assertNotNull(result);
        assertEquals("Gregory", result.getNombre());
        assertEquals("House", result.getApellido());
        assertEquals("ghouse@hospital.com", result.getEmail());
        
        verify(especialidadRepository, times(1)).findById(1L);
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    void create_EspecialidadNotFound_ThrowsException() {
        when(especialidadRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medicoService.create(medicoRequestDTO);
        });

        assertEquals("La especialidad no existe", exception.getMessage());
        
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void findById_Success() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));

        MedicoResponseDTO result = medicoService.findById(1L);

        assertNotNull(result);
        assertEquals("Gregory", result.getNombre());
    }

    @Test
    void findById_NotFound_ThrowsException() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medicoService.findById(99L);
        });

        assertEquals("Médico no encontrado con ID: 99", exception.getMessage());
    }

    @Test
    void findAll_Success() {
        when(medicoRepository.findAll()).thenReturn(List.of(medicoEntity));

        List<MedicoResponseDTO> result = medicoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Gregory", result.get(0).getNombre());
    }

    @Test
    void update_Success() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));
        when(especialidadRepository.findById(anyLong())).thenReturn(Optional.of(especialidadEntity));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoEntity);

        MedicoResponseDTO result = medicoService.update(1L, medicoRequestDTO);

        assertNotNull(result);
        assertEquals("Gregory", result.getNombre());
        verify(medicoRepository, times(1)).findById(1L);
        verify(especialidadRepository, times(1)).findById(1L);
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    void update_MedicoNotFound_ThrowsException() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medicoService.update(99L, medicoRequestDTO);
        });

        assertEquals("Médico no encontrado", exception.getMessage());
        verify(especialidadRepository, never()).findById(anyLong());
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void delete_Success() {
        when(medicoRepository.existsById(anyLong())).thenReturn(true);

        medicoService.delete(1L);

        verify(medicoRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_NotFound_ThrowsException() {
        when(medicoRepository.existsById(anyLong())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medicoService.delete(99L);
        });

        assertEquals("No se puede eliminar: Médico no encontrado", exception.getMessage());
        verify(medicoRepository, never()).deleteById(anyLong());
    }
}
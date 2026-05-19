package com.servicio_proveedores.ms_proveedores.service.impl;

import com.servicio_proveedores.ms_proveedores.dto.ProveedorRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.ProveedorResponseDTO;
import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.repository.ProveedorRepository;
import com.servicio_proveedores.ms_proveedores.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorServiceimpl implements ProveedorService {

    private final ProveedorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ProveedorResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorResponseDTO buscarPorId(Long id) {
        Proveedor proveedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el ID: " + id));
        return ProveedorResponseDTO.fromEntity(proveedor);
    }

    @Override
    @Transactional
    public ProveedorResponseDTO guardar(ProveedorRequestDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setRut(dto.getRut());
        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        proveedor.setDireccion(dto.getDireccion());

        Proveedor guardado = repository.save(proveedor);
        return ProveedorResponseDTO.fromEntity(guardado);
    }

    @Override
    @Transactional
    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedorExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el ID: " + id));
        
        proveedorExistente.setRut(dto.getRut());
        proveedorExistente.setNombre(dto.getNombre());
        proveedorExistente.setContacto(dto.getContacto());
        proveedorExistente.setTelefono(dto.getTelefono());
        proveedorExistente.setEmail(dto.getEmail());
        proveedorExistente.setDireccion(dto.getDireccion());

        Proveedor actualizado = repository.save(proveedorExistente);
        return ProveedorResponseDTO.fromEntity(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Proveedor no encontrado con el ID: " + id);
        }
        repository.deleteById(id);
    }
}
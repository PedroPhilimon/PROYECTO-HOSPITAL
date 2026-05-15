package com.servicio_inventario.ms_inventario.service.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.servicio_inventario.ms_inventario.client.CitaClient;
import com.servicio_inventario.ms_inventario.dto.MovimientoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.MovimientoResponseDTO;
import com.servicio_inventario.ms_inventario.model.MovimientoInventario;
import com.servicio_inventario.ms_inventario.model.Producto;
import com.servicio_inventario.ms_inventario.repository.MovimientoRepository;
import com.servicio_inventario.ms_inventario.repository.ProductoRepository;
import com.servicio_inventario.ms_inventario.service.MovimientoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final CitaClient citaClient;

    @Override
    @Transactional
    public List<MovimientoResponseDTO> findAll() {
        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoResponseDTO save(MovimientoRequestDTO dto) {
        if (dto.getCitaId() != null) {
            try {
                citaClient.buscarPorId(dto.getCitaId());
            } catch (Exception e) {
                throw new RuntimeException("Error La cita con ID " + dto.getCitaId() + " no existe.");
            }
        }

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if ("SALIDA".equalsIgnoreCase(dto.getTipoMovimiento())) {
            if (producto.getStock() < dto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente");
            }
            producto.setStock(producto.getStock() - dto.getCantidad());
        } else {
            producto.setStock(producto.getStock() + dto.getCantidad());
        }

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        movimiento.setCitaId(dto.getCitaId());

        productoRepository.save(producto);
        return MovimientoResponseDTO.fromEntity(movimientoRepository.save(movimiento));
    }

    @Override
    public MovimientoResponseDTO findByDto(Long id) {
        return movimientoRepository.findById(id)
                .map(MovimientoResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Override
    public MovimientoResponseDTO update(Long id, MovimientoRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
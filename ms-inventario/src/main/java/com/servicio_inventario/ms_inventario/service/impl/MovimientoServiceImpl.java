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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final CitaClient citaClient;

    @Override
    @Transactional
    public List<MovimientoResponseDTO> findAll() {
        log.info("Obteniendo todos los movimientos de inventario...");
        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoResponseDTO save(MovimientoRequestDTO dto) {
        log.info("Iniciando registro de movimiento ({}) para el producto ID: {}", dto.getTipoMovimiento(), dto.getProductoId());
        if (dto.getCitaId() != null) {
            log.info("Validando existencia de la cita asociada con ID: {}", dto.getCitaId());
            try {
                citaClient.buscarPorId(dto.getCitaId());
            } catch (Exception e) {
                log.error("Error en validación: La cita con ID {} no existe.", dto.getCitaId(), e);
                throw new RuntimeException("Error La cita con ID " + dto.getCitaId() + " no existe.");
            }
        }
        log.info("Buscando producto con ID: {}", dto.getProductoId());
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> {
                    log.error("Producto no encontrado con ID: {}", dto.getProductoId());
                    return new RuntimeException("Producto no encontrado");
                });

        if ("SALIDA".equalsIgnoreCase(dto.getTipoMovimiento())) {
            log.info("Procesando salida de inventario. Stock actual: {}, Cantidad a retirar: {}", producto.getStock(), dto.getCantidad());
            if (producto.getStock() < dto.getCantidad()) {
                log.error("Stock insuficiente para el producto ID: {}", producto.getId());
                throw new RuntimeException("Stock insuficiente");
            }
            producto.setStock(producto.getStock() - dto.getCantidad());
        } else {
            log.info("Procesando entrada de inventario. Stock actual: {}, Cantidad a ingresar: {}", producto.getStock(), dto.getCantidad());
            producto.setStock(producto.getStock() + dto.getCantidad());
        }

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        movimiento.setCitaId(dto.getCitaId());

        productoRepository.save(producto);
        log.info("Stock del producto ID: {} actualizado correctamente a: {}", producto.getId(), producto.getStock());
        return MovimientoResponseDTO.fromEntity(movimientoRepository.save(movimiento));
    }

    @Override
    public MovimientoResponseDTO findByDto(Long id) {
        log.info("Buscando movimiento de inventario con ID: {}", id);
        return movimientoRepository.findById(id)
                .map(MovimientoResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Override
    @Transactional
    public MovimientoResponseDTO update(Long id, MovimientoRequestDTO dto) {
        MovimientoInventario movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se encontró el movimiento con ID: {}", id);
                    return new RuntimeException("Movimiento no encontrado");
                });
        
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setCitaId(dto.getCitaId());
        
        return MovimientoResponseDTO.fromEntity(movimientoRepository.save(movimiento));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Intentando eliminar movimiento de inventario con ID: {}", id);
        if (!movimientoRepository.existsById(id)) {
            log.error("Error al eliminar: Movimiento no encontrado con ID: {}", id);
            throw new RuntimeException("No se puede eliminar: movimiento no encontrado");
        }
        movimientoRepository.deleteById(id);
    }

    private void validarCita(Long citaId) {
        if (citaId != null) {
            log.info("Validando existencia de la cita con ID: {}", citaId);
            try {
                citaClient.buscarPorId(citaId);
            } catch (Exception e) {
                log.error("Validación fallida: La cita con ID {} no existe.", citaId, e);
                throw new RuntimeException("Error: La cita con ID " + citaId + " no existe.");
            }
        }
    }

    private void actualizarStock(Producto producto, String tipo, Integer cantidad) {
        if ("SALIDA".equalsIgnoreCase(tipo)) {
            if (producto.getStock() < cantidad) {
                log.info("Actualizando stock (SALIDA). Actual: {}, a retirar: {}", producto.getStock(), cantidad);
                throw new RuntimeException("Stock insuficiente");
            }
            producto.setStock(producto.getStock() - cantidad);
        } else {
            log.info("Actualizando stock (ENTRADA). Actual: {}, a ingresar: {}", producto.getStock(), cantidad);
            producto.setStock(producto.getStock() + cantidad);
        }
    }
}
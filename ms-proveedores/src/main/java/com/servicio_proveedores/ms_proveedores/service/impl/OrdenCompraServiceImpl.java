package com.servicio_proveedores.ms_proveedores.service.impl;

import com.servicio_proveedores.ms_proveedores.client.FacturaClient;
import com.servicio_proveedores.ms_proveedores.client.InventarioClient;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import com.servicio_proveedores.ms_proveedores.model.Proveedor;
import com.servicio_proveedores.ms_proveedores.repository.OrdenCompraRepository;
import com.servicio_proveedores.ms_proveedores.repository.ProveedorRepository;
import com.servicio_proveedores.ms_proveedores.service.OrdenCompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private final OrdenCompraRepository repository;
    private final ProveedorRepository proveedorRepository;
    private final InventarioClient inventarioClient;
    private final FacturaClient facturaClient;

    @Override
    @Transactional
    public List<OrdenCompraResponseDTO> listarTodas() {
        log.info("Obteniendo todas las órdenes de compra...");
        return repository.findAll().stream()
                .map(OrdenCompraResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO buscarPorId(Long id) {
        log.info("Buscando orden de compra con ID: {}", id);
        OrdenCompra orden = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Orden de compra no encontrada con el ID: {}", id);
                    return new RuntimeException("Orden de compra no encontrada con el ID: " + id);
                });
        return OrdenCompraResponseDTO.fromEntity(orden);
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO guardar(OrdenCompraRequestDTO dto) {
        log.info("Iniciando creación de orden de compra para el proveedor ID: {}", dto.getIdProveedor());
        if (dto.getIdProveedor() == null) {
            log.error("ID de proveedor no proporcionado en la solicitud de orden de compra.");
            throw new IllegalArgumentException("Debe proporcionar un ID de proveedor válido.");
        }

        Long proveedorId = dto.getIdProveedor();

        log.info("Validando existencia del proveedor con ID: {}", proveedorId);
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> {
                    log.error("El proveedor con ID {} no existe.", proveedorId);
                    return new IllegalArgumentException("El proveedor con ID " + proveedorId + " no existe.");
                });

        log.info("Validando stock para el ítem ID: {} con cantidad: {}", dto.getIdItemInventario(), dto.getCantidadPedida());
        ResponseEntity<Boolean> respuestaStock = inventarioClient.validarStock(
                dto.getIdItemInventario(),
                dto.getCantidadPedida()
        );

        if (respuestaStock.getBody() == null || !respuestaStock.getBody()) {
            log.error("Stock insuficiente o ítem no disponible (ID: {}) en ms-inventario.", dto.getIdItemInventario());
            throw new RuntimeException("No hay stock suficiente o el ítem con ID "
                    + dto.getIdItemInventario() + " no está disponible en ms-inventario.");
        }

        OrdenCompra orden = new OrdenCompra();
        orden.setIdItemInventario(dto.getIdItemInventario());
        orden.setCantidadPedida(dto.getCantidadPedida());
        orden.setFechaPedido(dto.getFechaPedido());
        orden.setMontoEstimado(dto.getMontoEstimado());
        orden.setEstado(dto.getEstado());
        orden.setProveedor(proveedor);

        OrdenCompra guardada = repository.save(orden);
        log.info("Orden de compra guardada exitosamente con ID: {}", guardada.getIdOrden());

        if ("APROBADA".equalsIgnoreCase(guardada.getEstado())) {
            enviarAFacturacion(guardada);
        }

        return OrdenCompraResponseDTO.fromEntity(guardada);
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO actualizar(Long id, OrdenCompraRequestDTO dto) {
        log.info("Actualizando orden de compra con ID: {}", id);
        OrdenCompra ordenExistente = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Orden de compra no encontrada con el ID: {}", id);
                    return new RuntimeException("Orden de compra no encontrada con el ID: " + id);
                });

        if (dto.getIdProveedor() == null) {
            log.error("ID de proveedor no proporcionado en la actualización.");
            throw new IllegalArgumentException("Debe proporcionar un ID de proveedor válido.");
        }

        Long proveedorId = dto.getIdProveedor();

        log.info("Validando existencia del proveedor con ID: {} para la actualización", proveedorId);
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> {
                    log.error("El proveedor con ID {} no existe.", proveedorId);
                    return new IllegalArgumentException("El proveedor con ID " + proveedorId + " no existe.");
                });

        ordenExistente.setIdItemInventario(dto.getIdItemInventario());
        ordenExistente.setCantidadPedida(dto.getCantidadPedida());
        ordenExistente.setFechaPedido(dto.getFechaPedido());
        ordenExistente.setMontoEstimado(dto.getMontoEstimado());
        ordenExistente.setEstado(dto.getEstado());
        ordenExistente.setProveedor(proveedor);

        OrdenCompra actualizada = repository.save(ordenExistente);
        log.info("Orden de compra con ID: {} actualizada exitosamente", actualizada.getIdOrden());

        if ("APROBADA".equalsIgnoreCase(actualizada.getEstado())) {
            enviarAFacturacion(actualizada);
        }

        return OrdenCompraResponseDTO.fromEntity(actualizada);
    }


    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Intentando eliminar orden de compra con ID: {}", id);
        if (!repository.existsById(id)) {
            log.error("No se puede eliminar. Orden de compra no encontrada con el ID: {}", id);
            throw new RuntimeException("No se puede eliminar. Orden de compra no encontrada con el ID: " + id);
        }
        repository.deleteById(id);
        log.info("Orden de compra con ID: {} eliminada correctamente", id);
    }

    private void enviarAFacturacion(OrdenCompra orden) {
        log.info("Enviando orden de compra ID: {} a facturación...", orden.getIdOrden());
        try {
            OrdenCompraResponseDTO dto = OrdenCompraResponseDTO.fromEntity(orden);
            facturaClient.generarFacturaDeOrden(dto);
            log.info("Orden de compra ID: {} facturada exitosamente a través del client", orden.getIdOrden());
        } catch (Exception e) {
            log.error("Error al intentar facturar la orden de compra automáticamente (ID: {}). Detalle: {}", orden.getIdOrden(), e.getMessage(), e);
            System.err.println("Error al intentar facturar la orden de compra automáticamente: " + e.getMessage());
        }
    }
}
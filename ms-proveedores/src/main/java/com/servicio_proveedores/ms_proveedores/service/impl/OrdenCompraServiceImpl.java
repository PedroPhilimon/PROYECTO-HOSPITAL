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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private final OrdenCompraRepository repository;
    private final ProveedorRepository proveedorRepository;
    private final InventarioClient inventarioClient;
    private final FacturaClient facturaClient;

    @Override
    @Transactional
    public List<OrdenCompraResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(OrdenCompraResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO buscarPorId(Long id) {
        OrdenCompra orden = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada con el ID: " + id));
        return OrdenCompraResponseDTO.fromEntity(orden);
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO guardar(OrdenCompraRequestDTO dto) {
        if (dto.getIdProveedor() == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID de proveedor válido.");
        }

        Long proveedorId = dto.getIdProveedor();

        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor con ID " + proveedorId + " no existe."));

        ResponseEntity<Boolean> respuestaStock = inventarioClient.validarStock(
                dto.getIdItemInventario(),
                dto.getCantidadPedida()
        );

        if (respuestaStock.getBody() == null || !respuestaStock.getBody()) {
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

        if ("APROBADA".equalsIgnoreCase(guardada.getEstado())) {
            enviarAFacturacion(guardada);
        }

        return OrdenCompraResponseDTO.fromEntity(guardada);
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO actualizar(Long id, OrdenCompraRequestDTO dto) {
        OrdenCompra ordenExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada con el ID: " + id));

        if (dto.getIdProveedor() == null || dto.getIdProveedor().getIdProveedor() == null) {
            throw new IllegalArgumentException("Debe proporcionar una entidad proveedor válida con su respectivo ID.");
        }

        Long proveedorId = dto.getIdProveedor().getIdProveedor();
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor con ID " + proveedorId + " no existe."));

        ordenExistente.setIdItemInventario(dto.getIdItemInventario());
        ordenExistente.setCantidadPedida(dto.getCantidadPedida());
        ordenExistente.setFechaPedido(dto.getFechaPedido());
        ordenExistente.setMontoEstimado(dto.getMontoEstimado());
        ordenExistente.setEstado(dto.getEstado());
        ordenExistente.setProveedor(proveedor);

        OrdenCompra actualizada = repository.save(ordenExistente);

        if ("APROBADA".equalsIgnoreCase(actualizada.getEstado())) {
            enviarAFacturacion(actualizada);
        }

        return OrdenCompraResponseDTO.fromEntity(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Orden de compra no encontrada con el ID: " + id);
        }
        repository.deleteById(id);
    }

    private void enviarAFacturacion(OrdenCompra orden) {
        try {
            OrdenCompraResponseDTO dto = OrdenCompraResponseDTO.fromEntity(orden);
            facturaClient.generarFacturaDeOrden(dto);
        } catch (Exception e) {
            System.err.println("Error al intentar facturar la orden de compra automáticamente: " + e.getMessage());
        }
    }
}
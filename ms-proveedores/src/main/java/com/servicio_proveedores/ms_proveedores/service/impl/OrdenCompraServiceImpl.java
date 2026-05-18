package com.servicio_proveedores.ms_proveedores.service.impl;

import com.servicio_proveedores.ms_proveedores.client.FacturaClient;
import com.servicio_proveedores.ms_proveedores.client.InventarioClient;
import com.servicio_proveedores.ms_proveedores.dto.OrdenCompraResponseDTO;
import com.servicio_proveedores.ms_proveedores.model.OrdenCompra;
import com.servicio_proveedores.ms_proveedores.repository.OrdenCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenCompraServiceImpl {

    private final OrdenCompraRepository repository;
    private final InventarioClient inventarioClient;
    private final FacturaClient facturaClient;


    @Transactional(readOnly = true)
    public List<OrdenCompra> listarTodas() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<OrdenCompra> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @SuppressWarnings("null")
    public OrdenCompra guardar(OrdenCompra orden) {

        ResponseEntity<Boolean> respuestaStock = inventarioClient.validarStock(
                orden.getIdItemInventario(), 
                orden.getCantidadPedida()
        );

        if (respuestaStock.getBody() == null || !respuestaStock.getBody()) {
            throw new RuntimeException("No hay stock suficiente o el ítem con ID " 
                    + orden.getIdItemInventario() + " no está disponible en ms-inventario.");
        }

        OrdenCompra ordenGuardada = repository.save(orden);

        if ("APROBADA".equalsIgnoreCase(ordenGuardada.getEstado())) {
            enviarAFacturacion(ordenGuardada);
        }

        return ordenGuardada;
    }

    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private void enviarAFacturacion(OrdenCompra orden) {
        try {
            OrdenCompraResponseDTO dto = OrdenCompraResponseDTO.fromEntity(orden);
            facturaClient.generarFacturaDeOrden(dto);

            System.err.println("Error al intentar facturar la orden de compra automáticamente: " + e.getMessage());
        }
    }
}
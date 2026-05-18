package com.servicio_facturacion.ms_facturacion.service.impl;

import com.servicio_facturacion.ms_facturacion.dto.DetalleResponseDTO;
import com.servicio_facturacion.ms_facturacion.model.DetalleFactura;
import com.servicio_facturacion.ms_facturacion.model.Factura;
import com.servicio_facturacion.ms_facturacion.repository.DetalleFacturaRepository;
import com.servicio_facturacion.ms_facturacion.repository.FacturaRepository;
import com.servicio_facturacion.ms_facturacion.service.DetalleFacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleFacturaServiceImpl implements DetalleFacturaService {

    private final DetalleFacturaRepository detalleFacturaRepository;
    private final FacturaRepository facturaRepository;

    @Override
    @Transactional
    public DetalleResponseDTO agregarDetalle(Long facturaId, DetalleFactura detalleFactura) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RuntimeException("No se puede agregar el detalle. Factura no encontrada con el ID: " + facturaId));

        if (detalleFactura.getCantidad() != null && detalleFactura.getPrecioUnitario() != null) {
            detalleFactura.setPrecioTotal(detalleFactura.getCantidad() * detalleFactura.getPrecioUnitario());
        } else {
            detalleFactura.setPrecioTotal(0.0);
        }

        detalleFactura.setFactura(factura);

        DetalleFactura detalleGuardado = detalleFacturaRepository.save(detalleFactura);

        actualizarMontosFactura(factura);

        return DetalleResponseDTO.fromEntity(detalleGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleResponseDTO> obtenerDetallesPorFactura(Long facturaId) {
        if (!facturaRepository.existsById(facturaId)) {
            throw new RuntimeException("Factura no encontrada con el ID: " + facturaId);
        }

        return detalleFacturaRepository.findAll().stream()
                .filter(detalle -> detalle.getFactura().getId().equals(facturaId))
                .map(DetalleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleResponseDTO obtenerDetallePorId(Long id) {
        DetalleFactura detalle = detalleFacturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de factura no encontrado con el ID: " + id));
        return DetalleResponseDTO.fromEntity(detalle);
    }

    @Override
    @Transactional
    public void eliminarDetalle(Long id) {
        DetalleFactura detalle = detalleFacturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. Detalle no encontrado con el ID: " + id));
        
        Factura facturaAsociada = detalle.getFactura();
        
        detalleFacturaRepository.delete(detalle);

        actualizarMontosFactura(facturaAsociada);
    }


    private void actualizarMontosFactura(Factura factura) {
        List<DetalleFactura> detalles = detalleFacturaRepository.findAll().stream()
                .filter(d -> d.getFactura().getId().equals(factura.getId()))
                .collect(Collectors.toList());

        double nuevoSubtotal = detalles.stream()
                .mapToDouble(DetalleFactura::getPrecioTotal)
                .sum();

        factura.setMontoSubtotal(nuevoSubtotal);
        factura.setMontoTotal(Math.max(0.0, nuevoSubtotal - factura.getMontoDescuento()));
        
        facturaRepository.save(factura);
    }
}
package com.servicio_facturacion.ms_facturacion.service.impl;

import com.servicio_facturacion.ms_facturacion.client.CitaClient;
import com.servicio_facturacion.ms_facturacion.client.PacienteClient;
import com.servicio_facturacion.ms_facturacion.dto.FacturaRequestDTO;
import com.servicio_facturacion.ms_facturacion.dto.FacturaResponseDTO;
import com.servicio_facturacion.ms_facturacion.model.Factura;
import com.servicio_facturacion.ms_facturacion.repository.FacturaRepository;
import com.servicio_facturacion.ms_facturacion.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final PacienteClient pacienteClient;
    private final CitaClient citaClient;

    @Override
    @Transactional
    public FacturaResponseDTO crearFactura(FacturaRequestDTO facturaRequestDTO) {
        try {
            pacienteClient.buscarPorId(facturaRequestDTO.getPacienteId());
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: El paciente con ID " + facturaRequestDTO.getPacienteId() + " no existe.");
        }

        try {
            citaClient.buscarPorId(facturaRequestDTO.getCitaId());
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: La cita médica con ID " + facturaRequestDTO.getCitaId() + " no existe.");
        }

        Factura factura = new Factura();
        factura.setCitaId(facturaRequestDTO.getCitaId());
        factura.setPacienteId(facturaRequestDTO.getPacienteId());
        factura.setFechaEmision(facturaRequestDTO.getFechaEmision());
        factura.setMontoSubtotal(facturaRequestDTO.getMontoSubtotal());
        factura.setMontoDescuento(facturaRequestDTO.getMontoDescuento());
        factura.setMontoTotal(facturaRequestDTO.getMontoTotal());
        factura.setEstado(facturaRequestDTO.getEstado());
        factura.setMedioPago(facturaRequestDTO.getMedioPago());

        Factura facturaGuardada = facturaRepository.save(factura);

        return FacturaResponseDTO.fromEntity(facturaGuardada);
    }

    @Override
    @Transactional
    public FacturaResponseDTO obtenerFacturaPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con el ID: " + id));
        return FacturaResponseDTO.fromEntity(factura);
    }

    @Override
    @Transactional
    public List<FacturaResponseDTO> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaRepository.findAll();
        return facturas.stream()
                .map(FacturaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarFactura(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. Factura no encontrada con el ID: " + id));
        facturaRepository.delete(factura);
    }
}
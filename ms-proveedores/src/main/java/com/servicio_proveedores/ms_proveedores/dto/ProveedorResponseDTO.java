package com.servicio_proveedores.ms_proveedores.dto;

import com.servicio_proveedores.ms_proveedores.model.Proveedor;

import lombok.Data;

@Data
public class ProveedorResponseDTO {
    private String rut;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;

    
    public static ProveedorResponseDTO fromEntity(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setRut(proveedor.getRut());
        dto.setNombre(proveedor.getNombre());
        dto.setContacto(proveedor.getContacto());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }
}
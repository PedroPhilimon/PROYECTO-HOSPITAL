package com.servicio_proveedores.ms_proveedores.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProveedorRequestDTO {
    @NotBlank(message = "el rut no puede estar vacío")
    private String rut;

    @NotBlank(message = "el nombre puede estar vacío")
    private String nombre;

    @NotBlank(message = "el contacto no puede estar vacío")
    private String contacto;

    @NotBlank(message = "el telefono no puede estar vacío")
    private String telefono;

    @NotBlank(message = "el email no puede estar vacío")
    private String email;

    @NotBlank(message = "la dirección no puede estar vacío")
    private String direccion;
}

package com.servicio_proveedores.ms_proveedores.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proveedores")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {
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

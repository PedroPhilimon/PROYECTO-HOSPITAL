package com.servicio_medicos.ms_medicos.Controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoDTO {

    @NotNull
    private Long id;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @Email
    private String email;
    @NotEmpty
    private String numero;

}

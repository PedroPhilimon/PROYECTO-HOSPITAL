package com.pacientes.servicio_pacientes.dto;

import java.time.LocalDate;

import com.pacientes.servicio_pacientes.model.Paciente;

import lombok.Data;

@Data
public class PacienteResponseDTO {
    private Long id;
    private String run;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String prevision;







    public static PacienteResponseDTO fromEntity(Paciente paciente) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(paciente.getId());
        dto.setRun(paciente.getRun());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setPrevision(paciente.getPrevision());
        return dto;
    }
}

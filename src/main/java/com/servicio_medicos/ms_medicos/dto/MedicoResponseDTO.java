package com.servicio_medicos.ms_medicos.dto;

import com.servicio_medicos.ms_medicos.model.Especialidad;
import com.servicio_medicos.ms_medicos.model.Medico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String numero;
    Especialidad especialidad;

    public static MedicoResponseDTO fromEntity(Medico medico) {
        MedicoResponseDTO dto = new MedicoResponseDTO();
        dto.setId(medico.getId());
        dto.setNombre(medico.getNombre());
        dto.setApellido(medico.getApellido());
        dto.setEmail(medico.getEmail());
        dto.setEspecialidad(medico.getEspecialidad());
        dto.setNumero(medico.getNumero());
        return dto;
    }
}

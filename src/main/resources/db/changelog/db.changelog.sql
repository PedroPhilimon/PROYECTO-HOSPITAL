CREATE TABLE citas (
    id_cita BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    paciente_id BIGINT NOT NULL,
    
    medico_id BIGINT NOT NULL,
    
    fecha DATE NOT NULL,
    
    hora TIME NOT NULL,
    
    estado VARCHAR(50) NOT NULL,
    
    motivo VARCHAR(255),
    
    observacion VARCHAR(500)
);
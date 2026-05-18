CREATE TABLE historiales_clinicos (
    id_historial BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    fecha_atencion DATETIME NOT NULL,
    diagnostico VARCHAR(255) NOT NULL,
    tratamiento VARCHAR(255) NOT NULL
);

CREATE TABLE registros_clinicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_historial BIGINT NOT NULL,
    peso VARCHAR(50),
    presion_arterial VARCHAR(50),
    temperatura VARCHAR(50),
    observaciones VARCHAR(255),
    CONSTRAINT fk_historial_registro FOREIGN KEY (id_historial) 
        REFERENCES historiales_clinicos(id_historial) ON DELETE CASCADE
);  
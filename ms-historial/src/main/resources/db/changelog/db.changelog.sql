-- liquibase formatted sql

-- changeset pedro:1
CREATE TABLE historiales_clinicos (
    id_historial BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    fecha_atencion DATETIME NOT NULL,
    diagnostico VARCHAR(255) NOT NULL,
    tratamiento VARCHAR(255) NOT NULL
);

-- changeset pedro:2
CREATE TABLE registros_clinicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_historial BIGINT NOT NULL,
    peso VARCHAR(50) NOT NULL,
    presion_arterial VARCHAR(50) NOT NULL,
    temperatura VARCHAR(50) NOT NULL,
    observaciones VARCHAR(255) NOT NULL,
    CONSTRAINT fk_historial_registro FOREIGN KEY (id_historial) 
        REFERENCES historiales_clinicos(id_historial) ON DELETE CASCADE
);
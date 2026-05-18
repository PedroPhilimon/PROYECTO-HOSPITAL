--liquibase formatted sql

--changeset pedro:1
CREATE TABLE especialidades (
    id_especialidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE medicos (
    id_medico BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    id_especialidad BIGINT,
    CONSTRAINT fk_medico_especialidad FOREIGN KEY (id_especialidad) REFERENCES especialidades(id_especialidad)
);
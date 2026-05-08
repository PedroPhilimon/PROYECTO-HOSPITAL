--liquibase formatted sql



--changeset pedro:1
CREATE TABLE medico (
    id_medico BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    prevision VARCHAR(255) NOT NULL
);

CREATE TABLE especialidad {
    id_especialidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
}


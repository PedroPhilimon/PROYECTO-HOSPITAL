--liquibase formatted sql
DROP TABLE IF EXISTS asignaciones_salas;
DROP TABLE IF EXISTS agendas_medicos;

--changeset pablo:1
CREATE TABLE agendas_medicos (
    id_agenda BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_medico BIGINT NOT NULL,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL
);

--changeset pablo:2
CREATE TABLE asignaciones_salas (
    id_asignacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_agenda BIGINT,
    id_sala BIGINT NOT NULL,
    motivo_bloqueo VARCHAR(255) NOT NULL
);
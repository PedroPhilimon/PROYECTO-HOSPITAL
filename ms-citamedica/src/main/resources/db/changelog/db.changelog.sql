--liquibase formatted sql

DROP TABLE IF EXISTS citas;
DROP TABLE IF EXISTS salas;
DROP TABLE IF EXISTS databasechangelog;
DROP TABLE IF EXISTS databasechangeloglock;

--changeset pedro:1
CREATE TABLE salas (
    id_sala BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(255)
);

--changeset pedro:2
CREATE TABLE citas (
    id_cita BIGINT AUTO_INCREMENT PRIMARY KEY,
    pacienteId BIGINT NOT NULL,
    medicoId BIGINT NOT NULL,
    sala_id BIGINT, -- Columna añadida para la relación
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    motivo VARCHAR(255),
    CONSTRAINT FK_SALA_CITA FOREIGN KEY (sala_id) REFERENCES salas(id_sala)
); 

--changeset pedro:3
INSERT INTO salas (nombre, ubicacion) VALUES ('Sala de Urgencias A', 'Piso 1 - Ala Norte');
INSERT INTO salas (nombre, ubicacion) VALUES ('Pabellón de Cirugía General', 'Piso 2 - Bloque B');
INSERT INTO salas (nombre, ubicacion) VALUES ('Consulta Pediatría', 'Piso 1 - Ala Sur');
INSERT INTO salas (nombre, ubicacion) VALUES ('Box de Cardiología', 'Piso 3 - Consultas Médicas');
INSERT INTO salas (nombre, ubicacion) VALUES ('Unidad de Cuidados Intensivos (UCI)', 'Piso 2 - Bloque C');
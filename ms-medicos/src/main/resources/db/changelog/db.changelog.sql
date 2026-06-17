--liquibase formatted sql
DROP TABLE IF EXISTS medicos;
DROP TABLE IF EXISTS especialidades;
DROP TABLE IF EXISTS databasechangelog;
DROP TABLE IF EXISTS databasechangeloglock;

--changeset pedro:1
CREATE TABLE especialidades (
    id_especialidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

--changeset pedro:2
CREATE TABLE medicos (
    id_medico BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    id_especialidad BIGINT,
    CONSTRAINT fk_medico_especialidad FOREIGN KEY (id_especialidad) REFERENCES especialidades(id_especialidad)
);

--changeset pedro:3
INSERT INTO especialidades (id_especialidad, nombre) VALUES (1, 'Medicina General');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (2, 'Pediatría');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (3, 'Cardiología');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (4, 'Dermatología');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (5, 'Neurología');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (6, 'Ginecología');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (7, 'Traumatología');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (8, 'Oftalmología');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (9, 'Psiquiatría');
INSERT INTO especialidades (id_especialidad, nombre) VALUES (10, 'Endocrinología');

--changeset pedro:4
INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (1, 'Carlos', 'Mendoza', 'carlos.mendoza@email.com', '+56911112222', 1);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (2, 'Ana', 'Silva', 'ana.silva@email.com', '+56922223333', 2);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (3, 'Alejandro', 'Rojas', 'alejandro.rojas@email.com', '+56933334444', 3);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (4, 'Patricia', 'Morales', 'patricia.morales@email.com', '+56944445555', 4);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (5, 'Ricardo', 'Herrera', 'ricardo.herrera@email.com', '+56955556666', 5);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (6, 'Elena', 'Castro', 'elena.castro@email.com', '+56966667777', 6);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (7, 'Gabriel', 'Fuentes', 'gabriel.fuentes@email.com', '+56977778888', 7);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (8, 'Sofía', 'Vergara', 'sofia.vergara@email.com', '+56988889999', 8);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (9, 'Roberto', 'Cárcamo', 'roberto.carcamo@email.com', '+56999990000', 9);

INSERT INTO medicos (id_medico, nombre, apellido, email, numero, id_especialidad) 
VALUES (10, 'Lucía', 'Fernández', 'lucia.fernandez@email.com', '+56900001111', 10);
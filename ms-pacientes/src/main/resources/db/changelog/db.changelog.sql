--liquibase formatted sql



--changeset pedro:1
CREATE TABLE paciente (
    id_paciente BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    prevision VARCHAR(255) NOT NULL

);

--changeset pablo:3
ALTER TABLE paciente ADD CONSTRAINT uc_paciente_run UNIQUE (run);

--changeset pedro:2
INSERT INTO paciente (run, nombre, apellido, fecha_nacimiento, prevision) VALUES
('12.345.678-9', 'Juan', 'Pérez', '1990-05-14', 'Fonasa'),
('15.234.567-8', 'María', 'González', '1985-11-22', 'Isapre'),
('18.765.432-1', 'Carlos', 'Rojas', '1992-03-10', 'Fonasa'),
('17.654.321-K', 'Ana', 'Muñoz', '1988-07-19', 'Isapre'),
('19.876.543-2', 'Pedro', 'Soto', '1995-01-30', 'Fonasa'),
('14.567.890-3', 'Camila', 'Torres', '1993-09-15', 'Isapre'),
('16.789.012-4', 'Diego', 'Ramírez', '1987-12-05', 'Fonasa'),
('13.210.987-5', 'Valentina', 'Flores', '1998-06-25', 'Isapre'),
('11.998.877-6', 'Javier', 'Herrera', '1991-04-11', 'Fonasa'),
('20.123.456-7', 'Fernanda', 'Vega', '1996-08-09', 'Isapre'),
('10.234.567-8', 'Ricardo', 'Castro', '1984-02-17', 'Fonasa'),
('21.345.678-9', 'Daniela', 'Morales', '1999-10-01', 'Isapre'),
('22.456.789-0', 'Sebastián', 'Navarro', '1994-03-28', 'Fonasa'),
('23.567.890-1', 'Paula', 'Ortega', '1986-05-13', 'Isapre'),
('24.678.901-2', 'Tomás', 'Silva', '1997-11-07', 'Fonasa'),
('25.789.012-3', 'Francisca', 'Reyes', '1990-01-21', 'Isapre'),
('26.890.123-4', 'Andrés', 'Mendoza', '1989-07-03', 'Fonasa'),
('27.901.234-5', 'Catalina', 'Fuentes', '1992-12-14', 'Isapre'),
('28.012.345-6', 'Felipe', 'Aguilar', '1995-09-29', 'Fonasa'),
('29.123.456-7', 'Josefa', 'Paredes', '2000-04-18', 'Isapre');


--changeset evan:2
CREATE TABLE IF NOT EXISTS historial_paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diagnostico VARCHAR(500),
    antecedentes VARCHAR(500),
    tipo_sangre VARCHAR(10),
    id_paciente BIGINT NOT NULL,
    CONSTRAINT fk_historial_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);
--liquibase formatted sql
DROP TABLE IF EXISTS paciente;
DROP TABLE IF EXISTS historia_paciente;
DROP TABLE IF EXISTS databasechangelog;
DROP TABLE IF EXISTS databasechangeloglock;

--changeset pedro:1
CREATE TABLE paciente (
    id_paciente BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    prevision VARCHAR(255) NOT NULL
);

--changeset evan:2
CREATE TABLE IF NOT EXISTS historial_paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diagnostico VARCHAR(500),
    antecedentes VARCHAR(500),
    tipo_sangre VARCHAR(10),
    id_paciente BIGINT NOT NULL
);

--changeset pablo:3
ALTER TABLE paciente ADD CONSTRAINT uc_paciente_run UNIQUE (run);
ALTER TABLE historial_paciente ADD CONSTRAINT fk_historial_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente);

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

--changeset pablo:4
INSERT INTO historial_paciente (diagnostico, antecedentes, tipo_sangre, id_paciente) VALUES
('Hipertensión arterial bajo control', 'Fumador crónico hasta 2024, abuelo paterno con antecedentes cardíacos', 'O+', 1),
('Alergia severa a la penicilina', 'Asma estacional controlada con inhalador desde la infancia', 'A-', 2),
('Diabetes Tipo 2 en tratamiento', 'Madre diabética, sedentarismo severo', 'O-', 3),
('Fractura de muñeca izquierda en recuperación', 'Lesión deportiva jugando fútbol, sin cirugías previas', 'AB+', 4),
('Control crónico de Hipotiroidismo', 'Diagnosticado en 2022, toma levotiroxina de por vida', 'A+', 5),
('Rinitis alérgica estacional', 'Alergia al polen y al polvo, usa antihistamínicos en primavera', 'B+', 6),
('Asma bronquial moderada', 'Crisis recurrentes en invierno, usa inhalador de rescate', 'O+', 7),
('Gastritis crónica reagudizada', 'Consumo frecuente de antiinflamatorios por dolores de espalda', 'A-', 8),
('Insuficiencia renal leve', 'Seguimiento por nefrólogo, restricción de sodio en la dieta', 'B-', 9),
('Migraña con aura diagnosticada', 'Episodios mensuales gatillados por estrés y falta de sueño', 'O-', 10),
('Esguince de tobillo grado 2', 'Lesión en trayecto al trabajo, bajo tratamiento con kinesiólogo', 'O+', 11),
('Resistencia a la insulina', 'Tratamiento inicial con metformina y pauta nutricional', 'A+', 12),
('Control post-operatorio de apendicitis', 'Operado con éxito hace 6 meses, sin complicaciones posteriores', 'B+', 13),
('Anemia ferropénica en tratamiento', 'Suplementación con hierro oral por sospecha de baja ingesta', 'O+', 14),
('Evaluación por sospecha de celiaquía', 'Dolores abdominales recurrentes, exámenes de sangre pendientes', 'A-', 15),
('Hipercolesterolemia familiar', 'Padre y hermanos con niveles altos de colesterol, toma estatinas', 'O-', 16),
('Lumbago mecanopostural agudo', 'Dolor tras levantar carga pesada, reposo relativo por 3 días', 'B-', 17),
('Depresión leve en control', 'Tratamiento multidisciplinario con psicólogo y fármacos', 'O+', 18),
('Obesidad grado 1', 'Derivado a programa de vida sana con nutricionista', 'A+', 19),
('Chequeo médico general preventivo', 'Paciente sano, sin antecedentes mórbidos ni cirugías', 'AB-', 20);
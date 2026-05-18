--liquibase formatted sql

--changeset pedro:1
CREATE TABLE SALA_ATENCION (
    id_sala BIGINT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(100) NOT NULL,
    UBICACION VARCHAR(255)
);

--changeset pedro:2
CREATE TABLE citas (
    id_cita BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    sala_id BIGINT, -- Columna añadida para la relación
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    motivo VARCHAR(255),
    observacion VARCHAR(500),
    CONSTRAINT FK_SALA_CITA FOREIGN KEY (sala_id) REFERENCES SALA_ATENCION(id_sala)
); 
--liquibase formatted sql

--changeset pedro:1
CREATE TABLE consultas (
    id_consulta BIGINT AUTO_INCREMENT NOT NULL,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    cita_id BIGINT NOT NULL,
    fecha DATE NULL,
    motivo_consulta VARCHAR(255) NOT NULL,
    diagnostico VARCHAR(255) NOT NULL,
    observaciones TEXT NOT NULL,
    CONSTRAINT pk_consultas PRIMARY KEY (id_consulta)
);
--liquibase formatted sql

--changeset pedro:1
CREATE TABLE orden_laboratorio (
    id BIGINT AUTO_INCREMENT NOT NULL,
    consulta_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    fecha TIMESTAMP NOT NULL,
    CONSTRAINT pk_orden_laboratorio PRIMARY KEY (id)
);

--changeset pedro:2
CREATE TABLE examen_laboratorio (
    id BIGINT AUTO_INCREMENT NOT NULL,
    orden_id BIGINT NOT NULL,
    nombre_examen VARCHAR(255) NOT NULL,
    resultado VARCHAR(255),
    observacion VARCHAR(255),
    CONSTRAINT pk_examen_laboratorio PRIMARY KEY (id),
    CONSTRAINT fk_examen_orden FOREIGN KEY (orden_id) REFERENCES orden_laboratorio(id) ON DELETE CASCADE
);

CREATE TABLE agendas_medicos (
    id_agenda BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_medico BIGINT NOT NULL,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL
);

CREATE TABLE asignaciones_salas (
    id_asignacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_sala BIGINT NOT NULL,
    motivo_bloqueo VARCHAR(255),
    id_agenda BIGINT NOT NULL,
    CONSTRAINT fk_agenda_asignacion FOREIGN KEY (id_agenda) 
        REFERENCES agendas_medicos(id_agenda) ON DELETE CASCADE
);
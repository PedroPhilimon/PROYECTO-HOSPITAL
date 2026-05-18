CREATE TABLE recetas (
    id_receta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cita BIGINT NOT NULL,
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    fecha_emision DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL
);

CREATE TABLE detalle_receta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_receta BIGINT NOT NULL,
    id_medicamento BIGINT NOT NULL,
    cantidad_recetada INT NOT NULL,
    dosificacion VARCHAR(255) NOT NULL,
    CONSTRAINT fk_receta FOREIGN KEY (id_receta) 
        REFERENCES recetas(id_receta) ON DELETE CASCADE
);

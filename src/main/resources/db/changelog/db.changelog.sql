--liquibase formatted sql

--changeset pedro:1
CREATE TABLE productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    precio INT NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    fecha_vencimiento DATE
);

--changeset pedro:2
CREATE TABLE movimiento_inventario (
    id_movimiento BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_movimiento VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    fecha_nacimiento DATETIME NOT NULL,
    id_cita BIGINT,
    id_producto BIGINT,
    CONSTRAINT fk_movimiento_producto 
        FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);
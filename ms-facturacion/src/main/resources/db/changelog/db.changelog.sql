--liquibase formatted sql

--changeset pedro:1
CREATE TABLE facturas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cita_id BIGINT,
    paciente_id BIGINT,
    fecha_emision DATETIME,
    monto_subtotal DOUBLE,
    monto_descuento DOUBLE,
    monto_total DOUBLE,
    estado VARCHAR(255),
    medio_pago VARCHAR(255)
);

--changeset pedro:2
CREATE TABLE detalle_factura (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255),
    cantidad INT,
    precio_unitario DOUBLE,
    precio_total DOUBLE,
    id_factura BIGINT,
    FOREIGN KEY (id_factura) REFERENCES facturas(id)
);
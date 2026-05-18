
CREATE TABLE proveedores (
    id_proveedor BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(255)
);

CREATE TABLE ordenes_compra (
    id_orden BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_item_inventario BIGINT NOT NULL,
    cantidad_pedida INT NOT NULL,
    fecha_pedido DATETIME NOT NULL,
    monto_estimado DOUBLE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_proveedor BIGINT NOT NULL,
    CONSTRAINT fk_proveedor_orden FOREIGN KEY (id_proveedor) 
        REFERENCES proveedores(id_proveedor) ON DELETE CASCADE
);
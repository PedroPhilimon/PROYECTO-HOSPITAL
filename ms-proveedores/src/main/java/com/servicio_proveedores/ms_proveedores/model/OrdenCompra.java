package com.servicio_proveedores.ms_proveedores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long idOrden;

    @Column(name = "id_item_inventario")
    private Long idItemInventario;

    @Column(name = "cantidad_pedida")
    private Integer cantidadPedida;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Column(name = "monto_estimado")
    private Double montoEstimado;

    @NotBlank(message = "El estado de la orden ")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;
}
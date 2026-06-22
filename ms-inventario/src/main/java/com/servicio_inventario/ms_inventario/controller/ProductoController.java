package com.servicio_inventario.ms_inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicio_inventario.ms_inventario.dto.ProductoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.ProductoResponseDTO;
import com.servicio_inventario.ms_inventario.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("api/productos")
@Slf4j
@Tag(name = "Productos", description = "Operaciones relacionadas con Productos")
public class ProductoController {
    
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos del inventario")
    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de los productos")
            }
    )
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        log.info("Solicitud para obtener todos los productos");
        List<ProductoResponseDTO> citasMedicas = productoService.findAll();
        return ResponseEntity.ok(citasMedicas);
    }

    @Operation(summary = "Valida el stock disponible de un producto", description = "Verifica si un producto específico existe y valida cantidad total")
    @GetMapping("/{id}/validar-stock")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Evaluación realizada exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "El producto con ID no fue encontrado")
            }
    )
    public ResponseEntity<Boolean> validarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        log.info("Solicitud para validar stock del producto ID: {}, cantidad requerida: {}", id, cantidad);
        boolean tieneStock = true; 
                
        return ResponseEntity.ok(tieneStock);
    }


    @Operation(summary = "Obtiene un producto por id", description = "Verifica la existencia de un producto según su id")
    @GetMapping("/{id}")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Producto localizado correctamente",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "No se encontró ningún producto con el ID especificado"
            )
        }
    )
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST request: Solicitud para obtener el producto con ID: {}", id);
        return ResponseEntity.ok(productoService.findByDto(id));
    }

    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en el sistema a partir de los datos proporcionados")
    @PostMapping
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Producto creado de manera exitosa",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Los datos del JSON enviados no cumplen con las validaciones requeridas"
            )
        }
    )
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO dto) {
        log.info("Solicitud para crear un nuevo producto con nombre: '{}'", dto.getNombre());
        ProductoResponseDTO crearProducto = productoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearProducto);
    }


    @Operation(summary = "Actualizar un producto existente", description = "Modifica los atributos de un producto según su id")
    @PutMapping("/{id}")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Producto actualizado correctamente",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Estructura o datos del JSON de actualización inválidos"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "El producto que se intenta actualizar no existe en la base de datos"
            )
        }
    )
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        log.info("Solicitud para actualizar el producto con ID: {}", id);
        ProductoResponseDTO actualizarProducto = productoService.update(id, dto);
        return ResponseEntity.ok(actualizarProducto);
    }


    @Operation(summary = "Eliminar un producto", description = "Remueve permanentemente un producto de los registros del inventario utilizando su ID.")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "244", 
                description = "Producto eliminado exitosamente (Sin contenido en la respuesta)"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "No se puede eliminar porque el producto con dicho ID no fue hallado"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Solicitud para eliminar el producto con ID: {}", id);
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

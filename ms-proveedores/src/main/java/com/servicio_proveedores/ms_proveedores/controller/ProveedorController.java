package com.servicio_proveedores.ms_proveedores.controller;

import com.servicio_proveedores.ms_proveedores.dto.ProveedorRequestDTO;
import com.servicio_proveedores.ms_proveedores.dto.ProveedorResponseDTO;
import com.servicio_proveedores.ms_proveedores.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Operaciones relacionadas con la gestión de proveedores de insumos médicos")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    @Operation(summary = "Obtiene todos los proveedores", description = "Obtiene una lista de todos los proveedores registrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Proveedores encontrados exitosamente",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProveedorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No hay existencia de proveedores")
            }
    )
    public ResponseEntity<List<ProveedorResponseDTO>> findAll() {
        List<ProveedorResponseDTO> proveedores = proveedorService.listarTodos();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un proveedor específico", description = "Obtiene un proveedor específico según su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Proveedor encontrado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProveedorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado en la base de datos:")
            }
    )
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea un proveedor", description = "Crea un proveedor con todos sus detalles de contacto e insumos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProveedorResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error al crear proveedor")
            }
    )
    public ResponseEntity<ProveedorResponseDTO> create(@Valid @RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO crearProveedor = proveedorService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearProveedor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un proveedor", description = "Actualiza la información del proveedor y la guarda en la db")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente!",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProveedorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado D:")
            }
    )
    public ResponseEntity<ProveedorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO actualizarProveedor = proveedorService.actualizar(id, dto);
        return ResponseEntity.ok(actualizarProveedor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un proveedor específico", description = "Elimina un proveedor según su id")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "204", 
                        description = "Proveedor eliminado exitosamente. Sin contenido en el cuerpo."
                ),
                @ApiResponse(
                        responseCode = "404", 
                        description = "No se ha podido eliminar: El proveedor no fue encontrado con el ID proporcionado"
                )
        }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
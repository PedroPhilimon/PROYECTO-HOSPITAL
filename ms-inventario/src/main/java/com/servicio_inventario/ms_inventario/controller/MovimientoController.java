package com.servicio_inventario.ms_inventario.controller;

import com.servicio_inventario.ms_inventario.dto.MovimientoRequestDTO;
import com.servicio_inventario.ms_inventario.dto.MovimientoResponseDTO;
import com.servicio_inventario.ms_inventario.service.MovimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos de Inventario", description = "Operaciones para registrar y consultar el historial de entradas y salidas de stock")
public class MovimientoController {

    private final MovimientoService movimientoService;


    @Operation(summary = "Obtener todos los movimientos", description = "Retorna el historial completo de todos los movimientos de inventario (entradas y salidas) registrados en el sistema.")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Historial de movimientos obtenido exitosamente",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MovimientoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "No se encontraron movimientos registrados en el sistema"
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<MovimientoResponseDTO>> getAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }


    @Operation(summary = "Obtener un movimiento por su ID", description = "Busca y retorna la información detallada de un movimiento de inventario específico utilizando su identificador único proporcionado en la ruta.")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Movimiento localizado correctamente",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MovimientoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "El movimiento con el ID especificado no existe"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findByDto(id));
    }


    @Operation(summary = "Registrar un nuevo movimiento de inventario", description = "Crea un registro de entrada o salida de stock para un producto. Esta operación actualiza automáticamente las existencias actuales del producto afectado en el inventario.")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Movimiento registrado y stock actualizado con éxito",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MovimientoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Datos de solicitud inválidos o inconsistencia en el stock (por ejemplo, intentar una salida mayor al stock disponible)"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "El producto asociado al movimiento no fue encontrado"
            )
        }
    )
    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> create(@Valid @RequestBody MovimientoRequestDTO dto) {
        MovimientoResponseDTO nuevoMovimiento = movimientoService.save(dto);
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
    }


    @Operation(summary = "Actualizar un movimiento de inventario existente", description = "Modifica los detalles de un movimiento específico (como el tipo, la cantidad o la cita asociada) utilizando su ID en la ruta y los nuevos datos en el cuerpo de la petición.")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Movimiento actualizado exitosamente",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MovimientoResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Estructura del JSON inválida o datos inconsistentes con las reglas de negocio"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "No se encontró el registro del movimiento que se desea actualizar"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MovimientoRequestDTO dto) {
        MovimientoResponseDTO actualizarMovimiento = movimientoService.update(id, dto);
        return ResponseEntity.ok(actualizarMovimiento);
    }


    @Operation(summary = "Eliminar un registro de movimiento", description = "Remueve de forma permanente el registro de un movimiento de inventario del sistema utilizando su identificador único.")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "244", 
                description = "Movimiento eliminado correctamente (Sin contenido en el cuerpo de la respuesta)"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "No se pudo eliminar porque el ID del movimiento no existe en los registros"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
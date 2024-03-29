package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.client.ClienteUltimosSeguimientosDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestion de clientes")
public interface ClienteController {

    @Operation(summary = "Obtener todos los clientes", description = "Esta operación es para buscar todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-clientes')")
    ResponseEntity<GyManagerPage<ClienteDto>> getClientes(
            @Parameter(name = "fuzzySearch",
                    description = "busca por [nombre,apellido,numero_documento,email] segun el valor enviado")
            @RequestParam(name = "fuzzySearch", required = false, defaultValue = "") String fuzzySearch,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "NONE") ClienteSortBy sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(name = "matriculaVenceEn", required = false) Long matriculaVenceEn,
            @RequestParam(name = "matriculaVenceEnOverdue", required = false, defaultValue = "0") Long matriculaVenceEnOverdue,
            @RequestParam(name = "sinFinalizarRutinaEn", required = false) Long sinFinalizarRutinaEn);

    @Operation(summary = "Obtener todos los clientes con sus ultimos feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/ultimos-seguimientos", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-clientes')")
    ResponseEntity<GyManagerPage<ClienteUltimosSeguimientosDto>> getClientesByUltimosSeguimientos(
            @Parameter(name = "fuzzySearch",
                    description = "busca por [nombre,apellido,numero_documento,email] segun el valor enviado")
            @RequestParam(name = "fuzzySearch", required = false, defaultValue = "") String fuzzySearch,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "NONE") ClienteSortBy sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(name = "cantidadDias", required = false, defaultValue = "7") Long cantidadDias,
            @RequestParam(name = "idEstadoSeguimientoList") List<Long> idEstadoSeguimientoList);

    @Operation(summary = "Obtener un cliente por Id", description = "Esta operación es para buscar un cliente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idCliente}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-clientes')")
    ResponseEntity<ClienteDto> getClienteById(@PathVariable("idCliente") Long idCliente);

    @Operation(summary = "Agregar cliente", description = "Esta operación es para agregar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-clientes')")
    ResponseEntity<Long> addCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cliente request body.",
                    content = @Content(schema = @Schema(implementation = ClienteDto.class)), required = true)
            @RequestBody @Valid ClienteDto clienteDto);

    @Operation(summary = "Actualizar un cliente", description = "Esta operación es para actualizar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idCliente}", consumes = { "application/json"})
    ResponseEntity<Void> updateClienteById(
            @PathVariable("idCliente") Long idCliente,
            @RequestParam(name = "reactivate", required = false, defaultValue = "false") Boolean reactivate,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cliente request body.",
                    content = @Content(schema = @Schema(implementation = ClienteDto.class)), required = true)
            @RequestBody @Valid ClienteDto clienteDto);

    @Operation(summary = "Borrar un cliente", description = "Esta operación es para borrar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @DeleteMapping("/{idCliente}")
    @PreAuthorize("hasAuthority('delete-clientes')")
    ResponseEntity<Void> deleteClienteById(@PathVariable("idCliente") Long idCliente);
}

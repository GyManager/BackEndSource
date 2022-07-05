package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.clientes.ClienteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    ResponseEntity<List<ClienteDto>> getClientes(
            @Parameter(name = "fuzzySearch",
                    description = "busca por [nombre,apellido,numero_documento,email] segun el valor enviado")
            @RequestParam(name = "fuzzySearch", required = false) String fuzzySearch);

    @Operation(summary = "Obtener un cliente por Id", description = "Esta operación es para buscar un cliente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idCliente}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-clientes')")
    ResponseEntity<ClienteDto> getClienteById(@PathVariable("idCliente") Long idCliente);
}

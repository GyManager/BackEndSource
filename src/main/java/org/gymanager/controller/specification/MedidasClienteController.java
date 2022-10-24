package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.client.MedidasClienteSmallDto;
import org.gymanager.model.enums.MedidasClienteFilter;
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

@RequestMapping("/api/clientes/{idCliente}/medidas")
@Tag(name = "Clientes", description = "Gestion de clientes")
public interface MedidasClienteController {

    @Operation(summary = "Obtener medidas del cliente", description = """
            Esta operación es para buscar las medidas de un cliente""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-medidas')")
    ResponseEntity<List<MedidasClienteSmallDto>> getMedidasByIdCliente(@PathVariable("idCliente") Long idCliente);

    @Operation(summary = "Obtener una medida del cliente", description = """
            Esta operación es para obtener una medida del cliente""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idMedidas}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-medidas')")
    ResponseEntity<MedidasClienteDto> getMedidasClienteById(@PathVariable("idCliente") Long idCliente,
                                                            @PathVariable("idMedidas") Long idMedidas);

    @Operation(summary = "Cargar medidas del cliente", description = "Esta operación es para cargar las medidas del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-medidas')")
    ResponseEntity<Long> addMedidas(
            @PathVariable("idCliente") Long idCliente,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Medidas request body.",
                    content = @Content(schema = @Schema(implementation = MedidasClienteDto.class)), required = true)
            @RequestBody @Valid MedidasClienteDto medidasClienteDto);

    @Operation(summary = "Actualizar una medida de un cliente", description = """
            Esta operación es para actualizar una medida de un cliente""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idMedidas}", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-medidas')")
    ResponseEntity<Void> updateMedidasById(
            @PathVariable("idCliente") Long idCliente,
            @PathVariable("idMedidas") Long idMedidas,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Medidas request body.",
                    content = @Content(schema = @Schema(implementation = MedidasClienteDto.class)), required = true)
            @RequestBody @Valid MedidasClienteDto medidasClienteDto);

    @Operation(summary = "Borrar una medida del cliente", description = """
            Esta operación es para borrar una medidas del cliente""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @DeleteMapping("/{idMedidas}")
    @PreAuthorize("hasAuthority('delete-medidas')")
    ResponseEntity<Void> deleteMedidasById(@PathVariable("idCliente") Long idCliente,
                                           @PathVariable("idMedidas") Long idMedidas);
}

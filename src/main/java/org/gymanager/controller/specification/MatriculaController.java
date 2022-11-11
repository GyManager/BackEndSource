package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.model.enums.MatriculasFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/clientes/{idCliente}/matriculas")
@Tag(name = "Clientes", description = "Gestion de clientes")
public interface MatriculaController {

    @Operation(summary = "Obtener matriculas del cliente", description = """
            Esta operación es para buscar las matriculas de un cliente""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAnyAuthority('get-matriculas','get-mis-matriculas')")
    ResponseEntity<List<MatriculaDto>> getMatriculasByIdCliente(
            @PathVariable("idCliente") Long idCliente,
            @RequestParam(name = "matriculasFilter", required = false, defaultValue = "TODAS") MatriculasFilter matriculasFilter);

    @Operation(summary = "Cargar matricula del cliente", description = "Esta operación es para cargar la matricula del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-matriculas')")
    ResponseEntity<Long> addMatricula(
            @PathVariable("idCliente") Long idCliente,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Matricula request body.",
                    content = @Content(schema = @Schema(implementation = MatriculaDto.class)), required = true)
            @RequestBody @Valid MatriculaDto matriculaDto);

    @Operation(summary = "Borrar una matricula", description = "Esta operación es para borrar una matricula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @DeleteMapping("/{idMatricula}")
    @PreAuthorize("hasAuthority('delete-matriculas')")
    ResponseEntity<Void> deleteMatriculaById(@PathVariable("idCliente") Long idCliente,
                                           @PathVariable("idMatricula") Long idMatricula);
}

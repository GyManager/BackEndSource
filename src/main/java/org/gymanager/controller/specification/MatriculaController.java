package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.MatriculaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PreAuthorize("hasAuthority('get-clientes')")
    ResponseEntity<List<MatriculaDto>> getMatriculasByIdCliente(
            @PathVariable("idCliente") Long idCliente,
            @RequestParam(name = "last", required = false, defaultValue = "false") Boolean last);
}

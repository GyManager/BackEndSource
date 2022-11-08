package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.ClientsSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/dashboard")
@Tag(name = "Report and Summary", description = "General reporting and summary")
public interface DashboardController {

    @Operation(summary = "Obtener informacion de sumarizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-clientes')")
    ResponseEntity<ClientsSummary> getSummary(
            @RequestParam(required = false, defaultValue = "7") Long dayCountVencimientoMatricula,
            @RequestParam(required = false, defaultValue = "0") Long dayOverdueVencimientoMatricula,
            @RequestParam(required = false, defaultValue = "7") Long dayCountSinFinalizarDia
    );
}

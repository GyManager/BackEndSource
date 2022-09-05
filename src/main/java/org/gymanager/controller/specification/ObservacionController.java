package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.ObservacionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/micro-planes/{idMicroPlan}")
@Tag(name = "Micro Planes", description = "Gestion de micro planes")
public interface ObservacionController {

    @Operation(summary = "Obtener las observaciones de un micro plan por el Id del micro plan",
            description = "Esta operación es para obtener las observaciones de un micro plan por el Id del micro plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/observaciones", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-micro-planes')")
    ResponseEntity<List<ObservacionDto>> getObservacionesByIdMicroPlan(@PathVariable("idMicroPlan") Long idMicroPlan);
}

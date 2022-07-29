package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.HerramientaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "Herramientas", description = "Gestion de herramientas")
public interface HerramientaController {

    @Operation(summary = "Obtener las herramientas de un ejercicio por el Id del ejercicio",
            description = "Esta operación es para obtener las herramientas de un ejercicio por el Id del ejercicio", tags = "Ejercicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/ejercicios/{idEjercicio}/herramientas", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-ejercicios')")
    ResponseEntity<List<HerramientaDto>> getHerramientasByIdEjercicio(@PathVariable("idEjercicio") Long idEjercicio);

    @Operation(summary = "Obtener todas las herramientas",
            description = "Esta operación es para obtener todas las herramientas en existencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/herramientas", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-herramientas')")
    ResponseEntity<List<HerramientaDto>> getHerramientas();

}

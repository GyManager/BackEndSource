package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.PasoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "Ejercicios", description = "Gestion de ejercicios")
public interface PasoController {

    @Operation(summary = "Obtener los pasos de un ejercicio por el Id del ejercicio",
            description = "Esta operación es para obtener los pasos de un ejercicio por el Id del ejercicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/ejercicios/{idEjercicio}/pasos", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-ejercicios')")
    ResponseEntity<List<PasoDto>> getPasosByIdEjercicio(@PathVariable("idEjercicio") Long idEjercicio);

}

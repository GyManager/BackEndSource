package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.EjercicioDto;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/ejercicios")
@Tag(name = "Ejercicios", description = "Gestion de ejercicios")
public interface EjercicioController {

    @Operation(summary = "Obtener todos los ejercicios", description = "Esta operación es para buscar todos los ejercicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-ejercicios')")
    ResponseEntity<List<EjercicioDto>> getEjercicios();

    @Operation(summary = "Obtener un ejercicio por Id", description = "Esta operación es para buscar un ejercicio por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idEjercicio}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-ejercicios')")
    ResponseEntity<EjercicioDto> getEjercicioById(@PathVariable("idEjercicio") Long idEjercicio);

}

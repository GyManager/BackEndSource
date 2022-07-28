package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.EjercicioDtoRequest;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/api/ejercicios")
@Tag(name = "Ejercicios", description = "Gestion de ejercicios")
public interface EjercicioController {

    @Operation(summary = "Obtener todos los ejercicios", description = "Esta operación es para buscar todos los ejercicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-ejercicios')")
    ResponseEntity<GyManagerPage<EjercicioDto>> getEjercicios(
            @Parameter(name = "search",
                    description = "busca por [nombre, tipo de ejercicio]")
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "NONE") EjercicioSortBy sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction
    );

    @Operation(summary = "Obtener un ejercicio por Id", description = "Esta operación es para buscar un ejercicio por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idEjercicio}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-ejercicios')")
    ResponseEntity<EjercicioDto> getEjercicioById(@PathVariable("idEjercicio") Long idEjercicio);

    @Operation(summary = "Agregar ejercicio", description = "Esta operación es para agregar un ejercicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-ejercicios')")
    ResponseEntity<Long> addEjercicio(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Ejercicio request body.",
                    content = @Content(schema = @Schema(implementation = EjercicioDtoRequest.class)), required = true)
            @RequestBody @Valid EjercicioDtoRequest ejercicioDtoRequest);

    @Operation(summary = "Actualizar un ejercicio", description = "Esta operación es para actualizar un ejercicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idEjercicio}", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-ejercicios')")
    ResponseEntity<Void> updateEjercicioById(
            @PathVariable("idEjercicio") Long idEjercicio,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Ejercicio request body.",
                    content = @Content(schema = @Schema(implementation = EjercicioDtoRequest.class)), required = true)
            @RequestBody @Valid EjercicioDtoRequest ejercicioDtoRequest);
}

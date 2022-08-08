package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoRequest;
import org.gymanager.model.enums.MicroPlanSortBy;
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

@RequestMapping("/api/micro-planes")
@Tag(name = "Micro Planes", description = "Gestion de micro planes")
public interface MicroPlanController {

    @Operation(summary = "Obtener todos los Micro Planes", description = "Esta operación es para buscar todos los micro planes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-micro-planes')")
    ResponseEntity<GyManagerPage<MicroPlanDto>> getMicroPlanes(
            @Parameter(name = "search",
                    description = "busca por [nombre]")
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "NONE") MicroPlanSortBy sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction
    );

    @Operation(summary = "Obtener un micro plan por Id", description = "Esta operación es para buscar un micro plan por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idMicroPlan}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-micro-planes')")
    ResponseEntity<MicroPlanDto> getMicroPlanById(@PathVariable("idMicroPlan") Long idMicroPlan);

    @Operation(summary = "Agregar micro plan", description = "Esta operación es para agregar un micro plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-micro-planes')")
    ResponseEntity<Long> addMicroPlan(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Micro plan request body.",
                    content = @Content(schema = @Schema(implementation = MicroPlanDtoRequest.class)), required = true)
            @RequestBody @Valid MicroPlanDtoRequest microPlanDtoRequest);

    @Operation(summary = "Actualizar un micro plan", description = "Esta operación es para actualizar un micro plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idMicroPlan}", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-micro-planes')")
    ResponseEntity<Void> updateMicroPlanById(
            @PathVariable("idMicroPlan") Long idMicroPlan,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Micro plan request body.",
                    content = @Content(schema = @Schema(implementation = MicroPlanDtoRequest.class)), required = true)
            @RequestBody @Valid MicroPlanDtoRequest microPlanDtoRequest);
}

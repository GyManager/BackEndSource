package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.client.PlanDtoDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@Tag(name = "Planes", description = "Gestion de planes")
public interface PlanController {


    @Operation(summary = "Obtener todos los Planes de un cliente",
            description = "Esta operación es para obtener todos los planes de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value= "/clientes/{idCliente}/planes", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-planes')")
    ResponseEntity<List<PlanDto>> getPlansByClientId(@PathVariable("idCliente") Long idCliente);

    @Operation(summary = "Obtener un plan por Id", description = "Esta operación es para buscar un plan por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/planes/{idPlan}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-planes')")
    ResponseEntity<PlanDto> getPlanById(@PathVariable("idPlan") Long idPlan);

    @Operation(summary = "Agregar plan", description = "Esta operación es para agregar un plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(value = "/clientes/{idCliente}/planes", produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-planes')")
    ResponseEntity<Long> addPlan(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Plan request body.",
                    content = @Content(schema = @Schema(implementation = PlanDtoDetails.class)), required = true)
            @RequestBody @Valid PlanDtoDetails planDtoDetails);

    @Operation(summary = "Actualizar un plan", description = "Esta operación es para actualizar un plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/clientes/{idCliente}/planes/{idPlan}", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-planes')")
    ResponseEntity<Void> updatePlanById(
            @PathVariable("idCliente") Long idCliente,
            @PathVariable("idPlan") Long idPlan,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Plan request body.",
                    content = @Content(schema = @Schema(implementation = PlanDtoDetails.class)), required = true)
            @RequestBody @Valid PlanDtoDetails planDtoDetails);
}

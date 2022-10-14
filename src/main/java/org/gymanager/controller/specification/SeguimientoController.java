package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.SeguimientoFinDiaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api")
@Tag(name = "Planes", description = "Gestion de planes")
public interface SeguimientoController {

    @Operation(summary = "Cargar el seguimiento del final del dia para una rutina")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    @PostMapping(value = "/planes/{idPlan}/micro-planes/{idMicroPlan}/rutinas/{idRutina}/seguimientos"
            , produces = { "application/json"})
    @PreAuthorize("hasAuthority('post-mis-feedback')")
    ResponseEntity<Long> addSeguimientoEjercicio(
            @PathVariable("idPlan") Long idPlan,
            @PathVariable("idMicroPlan") Long idMicroPlan,
            @PathVariable("idRutina") Long idRutina,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Seguimiento request body.",
                    content = @Content(schema = @Schema(implementation = SeguimientoFinDiaDto.class)), required = true)
            @RequestBody@Valid SeguimientoFinDiaDto seguimientoFinDiaDto);
}

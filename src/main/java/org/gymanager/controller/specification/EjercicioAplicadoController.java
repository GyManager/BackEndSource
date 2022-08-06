package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.EjercicioAplicadoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "Micro Planes", description = "Gestion de micro planes")
public interface EjercicioAplicadoController {

    @Operation(summary = "Obtener los ejercicios aplicados de una rutina por el id de rutina",
            description = "Esta operación es para obtener los ejercicios aplicados de una rutina por el id de rutina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/micro-planes/{idMicroPlan}/rutinas/{idRutina}/ejercicios-aplicados", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-micro-planes')")
    ResponseEntity<List<EjercicioAplicadoDto>> getEjerciciosAplicadosByIdRutina(@PathVariable("idMicroPlan") Long idMicroPlan,
                                                                                @PathVariable("idRutina") Long idRutina);
}

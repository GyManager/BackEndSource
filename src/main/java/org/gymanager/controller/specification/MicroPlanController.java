package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}

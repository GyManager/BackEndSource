package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "Roles", description = "Gestion de roles")
public interface RolController {

    @Operation(summary = "Obtener todos los roles", description = "Esta operación es para buscar todos los roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/roles",produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-roles')")
    ResponseEntity<List<String>> getRoles();
}

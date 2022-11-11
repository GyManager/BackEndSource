package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.enums.ClienteSortBy;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api")
@Tag(name = "Clientes", description = "Gestion de clientes")
public interface ExportController {

    @Operation(summary = "Obtener todos los clientes", description = "Esta operación es para buscar todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/clientes-export", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-clientes')")
    void getClientes(
            @Parameter(name = "fuzzySearch",
                    description = "busca por [nombre,apellido,numero_documento,email] segun el valor enviado")
            @RequestParam(name = "fuzzySearch", required = false, defaultValue = "") String fuzzySearch,
            @RequestParam(name = "sortBy", required = false, defaultValue = "NONE") ClienteSortBy sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(name = "matriculaVenceEn", required = false) Long matriculaVenceEn,
            @RequestParam(name = "matriculaVenceEnOverdue", required = false, defaultValue = "0") Long matriculaVenceEnOverdue,
            @RequestParam(name = "sinFinalizarRutinaEn", required = false) Long sinFinalizarRutinaEn,
            HttpServletResponse response);
}

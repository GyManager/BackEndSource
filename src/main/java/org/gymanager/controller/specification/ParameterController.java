package org.gymanager.controller.specification;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "Parametros", description = "Parametros")
public interface ParameterController {

    @Operation(summary = "Obtener los tipos de ejercicio disponibles",
            description = "Esta operación es para obtener los tipos de ejercicio disponibles")
    @GetMapping(value = "/tipo-ejercicios", produces = { "application/json"})
    ResponseEntity<List<String>> getTipoEjercicios();

    @Operation(summary = "Obtener los objetivos disponibles",
            description = "Esta operación es para obtener los objetivos disponibles")
    @GetMapping(value = "/objetivos", produces = { "application/json"})
    ResponseEntity<List<String>> getObjetivos();

    @Operation(summary = "Obtener los sexos disponibles",
            description = "Esta operación es para obtener los sexos disponibles")
    @GetMapping(value = "/sexos", produces = { "application/json"})
    ResponseEntity<List<String>> getSexos();

    @Operation(summary = "Obtener los tipos de documento disponibles",
            description = "Esta operación es para obtener los tipos de documento disponibles")
    @GetMapping(value = "/tipo-documentos", produces = { "application/json"})
    ResponseEntity<List<String>> getTipoDocumentos();

    @Operation(summary = "Obtener los bloques disponibles",
            description = "Esta operación es para obtener los bloques disponibles")
    @GetMapping(value = "/bloques", produces = { "application/json"})
    ResponseEntity<List<String>> getBloques();
}

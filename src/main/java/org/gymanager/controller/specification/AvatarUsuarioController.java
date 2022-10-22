package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.AvatarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios")
public interface AvatarUsuarioController {

    @Operation(summary = "Obtener el avatar de un usuario por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idUsuario}/avatar", produces = { "application/json"})
    ResponseEntity<String> getAvatarUsuarioById(@PathVariable("idUsuario") Long idUsuario);

    @Operation(summary = "Actualizar el avatar de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idUsuario}/avatar", consumes = { "application/json"})
    ResponseEntity<Void> updateAvatarUsuarioById(
            @PathVariable("idUsuario") Long idUsuario,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Avatar request body.",
                    content = @Content(schema = @Schema(implementation = AvatarDto.class)), required = true)
            @RequestBody @Valid AvatarDto avatarDto);
}

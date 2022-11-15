package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.UsuarioRecovery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Gestion de usuarios")
public interface RecoveryController {

    @Operation(summary = "Reiniciar contraseña de un usuario por mai",
            description = "Esta operación es para reiniciar contraseña de un usuario y enviarla a su mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PostMapping(value = "/recovery", consumes = { "application/json"})
    ResponseEntity<Void> resetPasswordUsuarioByMail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario request body.",
                    content = @Content(schema = @Schema(implementation = UsuarioRecovery.class)), required = true)
            @RequestBody @Valid UsuarioRecovery usuarioRecovery);
}

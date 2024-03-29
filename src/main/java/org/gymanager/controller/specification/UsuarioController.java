package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.client.UsuarioInfoDto;
import org.gymanager.model.client.UsuarioPasswordDto;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios")
public interface UsuarioController {

    @Operation(summary = "Obtener todos los usuarios", description = "Esta operación es para buscar todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-usuarios')")
    ResponseEntity<GyManagerPage<UsuarioDto>> getUsuarios(
            @Parameter(name = "search",
                    description = "busca por [nombre, apellido, numero_documento, email] segun el valor enviado")
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "NONE") UsuarioSortBy sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction
    );

    @Operation(summary = "Obtener un usuario por Id", description = "Esta operación es para buscar un usuario por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idUsuario}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-usuarios')")
    ResponseEntity<UsuarioDtoDetails> getUsuarioById(@PathVariable("idUsuario") Long idUsuario);

    @Operation(summary = "Agregar usuario", description = "Esta operación es para agregar un usuario. Se valida \n" +
            "* El nombre del usuario es obligatorio \n" +
            "* La contraseña es obligatoria \n" +
            "* La contraseña debe tener entre 8 y 25 caracteres. \n" +
            "* La contraseña debe contener al menos un numero, una mayuscula y una minuscula \n" +
            "* El mail es obligatorio y debe tener un formato de mail correcto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(produces = { "application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAuthority('post-usuarios')")
    ResponseEntity<Long> addUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario request body.",
                    content = @Content(schema = @Schema(implementation = UsuarioDtoDetails.class)), required = true)
            @RequestBody @Valid UsuarioDtoDetails usuarioDtoDetails);

    @Operation(summary = "Actualizar un usuario", description = "Esta operación es para actualizar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idUsuario}", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-usuarios')")
    ResponseEntity<Void> updateUsuarioById(
            @PathVariable("idUsuario") Long idUsuario,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario request body.",
                    content = @Content(schema = @Schema(implementation = UsuarioDtoDetails.class)), required = true)
            @RequestBody @Valid UsuarioDtoDetails usuarioDtoDetails);

    @Operation(summary = "Actualizar mi usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/mis-datos", consumes = { "application/json"})
    ResponseEntity<Void> updateMiUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario request body.",
                    content = @Content(schema = @Schema(implementation = UsuarioDto.class)), required = true)
            @RequestBody @Valid UsuarioDto usuarioDto);

    @Operation(summary = "Borrar un usuario", description = "Esta operación es para borrar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasAuthority('delete-usuarios')")
    ResponseEntity<Void> deleteUsuarioById(@PathVariable("idUsuario") Long idUsuario);

    @Operation(summary = "Obtener un usuario por token", description = """
            Esta operación es para buscar un usuario por token activo""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/info", produces = { "application/json"})
    ResponseEntity<UsuarioInfoDto> getUsuarioByToken();

    @Operation(summary = "Actualizar contraseña de un usuario",
            description = "Esta operación es para actualizar contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idUsuario}/password", consumes = { "application/json"})
    ResponseEntity<Void> updatePasswordUsuarioById(
            @PathVariable("idUsuario") Long idUsuario,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario request body.",
                    content = @Content(schema = @Schema(implementation = UsuarioDtoDetails.class)), required = true)
            @RequestBody @Valid UsuarioPasswordDto usuarioPasswordDto);

    @Operation(summary = "Reiniciar contraseña de un usuario",
            description = "Esta operación es para reiniciar contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idUsuario}/password-reset", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-usuarios')")
    ResponseEntity<Void> resetPasswordUsuarioById(@PathVariable("idUsuario") Long idUsuario);
}

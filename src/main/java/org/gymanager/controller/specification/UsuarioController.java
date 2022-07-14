package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios")
public interface UsuarioController {

    @Operation(summary = "Obtener todos los usuarios", description = "Esta operación es para buscar todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-usuarios')")
    ResponseEntity<List<UsuarioDto>> getUsuarios();

    @Operation(summary = "Obtener un usuario por Id", description = "Esta operación es para buscar un usuario por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "/{idUsuario}", produces = { "application/json"})
    @PreAuthorize("hasAuthority('get-usuarios')")
    ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable("idUsuario") Long idUsuario);

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
    ResponseEntity<Long> addUsuario(@RequestBody @Valid UsuarioDtoRegistro usuarioDtoRegistro);

    @Operation(summary = "Actualizar un usuario", description = "Esta operación es para actualizar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @PutMapping(value = "/{idUsuario}", consumes = { "application/json"})
    @PreAuthorize("hasAuthority('put-usuarios')")
    ResponseEntity<Void> updateUsuarioById(@PathVariable("idUsuario") Long idUsuario,
                                     @RequestBody @Valid UsuarioDtoRegistro usuarioDtoRegistro);

    @Operation(summary = "Borrar un usuario", description = "Esta operación es para borrar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasAuthority('delete-usuarios')")
    ResponseEntity<Void> deleteUsuarioById(@PathVariable("idUsuario") Long idUsuario);
}

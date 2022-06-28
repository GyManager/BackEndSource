package org.gymanager.controller.specification;

import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;
import org.springframework.http.ResponseEntity;
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
public interface UsuarioController {

    @PostMapping
    ResponseEntity<UsuarioDto> addUsuario(@RequestBody @Valid UsuarioDtoRegistro usuarioDtoRegistro);

    @GetMapping
    ResponseEntity<List<UsuarioDto>> getUsuarios();

    @GetMapping("/{idUsuario}")
    ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable("idUsuario") Long idUsuario);

    @PutMapping("/{idUsuario}")
    ResponseEntity<Void> updateUsuarioById(@PathVariable("idUsuario") Long idUsuario,
                                     @RequestBody @Valid UsuarioDtoRegistro usuarioDtoRegistro);

    @DeleteMapping("/{idUsuario}")
    ResponseEntity<Void> deleteUsuarioById(@PathVariable("idUsuario") Long idUsuario);
}

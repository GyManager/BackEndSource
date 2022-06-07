package org.gymanager.controller.specification;

import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/usuarios")
public interface UsuarioController {

    @PostMapping
    ResponseEntity<UsuarioDto> addUsuario(@RequestBody UsuarioDtoRegistro usuarioDtoRegistro);

    @GetMapping
    ResponseEntity<List<UsuarioDto>> getUsuarios();
}

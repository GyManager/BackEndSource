package org.gymanager.controller.specification;

import org.gymanager.model.client.usuarios.UsuarioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/usuarios")
public interface UsuarioController {

    @GetMapping
    ResponseEntity<List<UsuarioDto>> getUsuarios();
}

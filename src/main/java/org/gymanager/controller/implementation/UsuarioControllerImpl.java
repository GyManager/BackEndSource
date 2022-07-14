package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.UsuarioController;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<UsuarioDto>> getUsuarios() {
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    @Override
    public ResponseEntity<UsuarioDto> getUsuarioById(Long idUsuario) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(idUsuario));
    }

    @Override
    public ResponseEntity<Long> addUsuario(UsuarioDtoRegistro usuarioDtoRegistro) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.addUsuario(usuarioDtoRegistro));
    }

    @Override
    public ResponseEntity<Void> updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro) {
        usuarioService.updateUsuarioById(idUsuario, usuarioDtoRegistro);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUsuarioById(Long idUsuario) {
        usuarioService.deleteUsuarioById(idUsuario);
        return ResponseEntity.noContent().build();
    }
}

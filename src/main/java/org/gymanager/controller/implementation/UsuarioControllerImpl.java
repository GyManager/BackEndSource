package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.UsuarioController;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.orchestrator.specification.UsuarioOrchestrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    @NonNull
    private UsuarioOrchestrator usuarioOrchestrator;

    @Override
    public ResponseEntity<Long> addUsuario(UsuarioDtoRegistro usuarioDtoRegistro) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioOrchestrator.addUsuario(usuarioDtoRegistro));
    }

    @Override
    public ResponseEntity<List<UsuarioDto>> getUsuarios() {
        return ResponseEntity.ok(usuarioOrchestrator.getUsuarios());
    }

    @Override
    public ResponseEntity<UsuarioDto> getUsuarioById(Long idUsuario) {
        return ResponseEntity.ok(usuarioOrchestrator.getUsuarioById(idUsuario));
    }

    @Override
    public ResponseEntity<Void> updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro) {
        usuarioOrchestrator.updateUsuarioById(idUsuario, usuarioDtoRegistro);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUsuarioById(Long idUsuario) {
        usuarioOrchestrator.deleteUsuarioById(idUsuario);
        return ResponseEntity.noContent().build();
    }
}

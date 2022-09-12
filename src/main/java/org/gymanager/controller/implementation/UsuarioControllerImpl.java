package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.UsuarioController;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<GyManagerPage<UsuarioDto>> getUsuarios(String search, Integer page, Integer pageSize,
                                                                 UsuarioSortBy sortBy, Sort.Direction direction) {
        return ResponseEntity.ok(usuarioService.getUsuarios(search, page, pageSize, sortBy, direction));
    }

    @Override
    public ResponseEntity<UsuarioDtoDetails> getUsuarioById(Long idUsuario) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(idUsuario));
    }

    @Override
    public ResponseEntity<Long> addUsuario(UsuarioDtoDetails usuarioDtoDetails) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.addUsuario(usuarioDtoDetails));
    }

    @Override
    public ResponseEntity<Void> updateUsuarioById(Long idUsuario, UsuarioDtoDetails usuarioDtoDetails) {
        usuarioService.updateUsuarioById(idUsuario, usuarioDtoDetails);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUsuarioById(Long idUsuario) {
        usuarioService.deleteUsuarioById(idUsuario);
        return ResponseEntity.noContent().build();
    }
}

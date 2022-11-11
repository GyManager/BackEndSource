package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.AvatarUsuarioController;
import org.gymanager.controller.specification.UsuarioController;
import org.gymanager.model.client.AvatarDto;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.client.UsuarioInfoDto;
import org.gymanager.model.client.UsuarioPasswordDto;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.AvatarUsuarioService;
import org.gymanager.service.specification.UsuarioService;
import org.gymanager.utils.Permisos;
import org.gymanager.utils.UserPermissionValidation;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class AvatarUsuarioControllerImpl implements AvatarUsuarioController {

    @NonNull
    private AvatarUsuarioService avatarUsuarioService;

    @Override
    public ResponseEntity<String> getAvatarUsuarioById(Long idUsuario) {
        return ResponseEntity.ok(avatarUsuarioService.getAvatarUsuarioById(idUsuario));
    }

    @Override
    public ResponseEntity<Void> updateAvatarUsuarioById(Long idUsuario, AvatarDto avatarDto) {
        avatarUsuarioService.updateAvatarUsuarioById(idUsuario, avatarDto);
        return ResponseEntity.noContent().build();
    }
}

package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.client.AvatarDto;
import org.gymanager.model.domain.Avatar;
import org.gymanager.repository.specification.AvatarRepository;
import org.gymanager.service.specification.AvatarUsuarioService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarUsuarioServiceImpl implements AvatarUsuarioService {

    @NonNull
    private AvatarRepository avatarRepository;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public String getAvatarUsuarioById(Long idUsuario) {
        return avatarRepository.findById(idUsuario).map(Avatar::getImagen).orElse("");
    }

    @Override
    public void updateAvatarUsuarioById(Long idUsuario, AvatarDto avatarDto) {
        if(!usuarioService.getUsuarioEntityFromCurrentToken().getIdUsuario().equals(idUsuario)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "El usuario no tiene permitido actualizar imagenes de otros usuarios");
        }

        var avatar = avatarRepository.findById(idUsuario).orElseGet(Avatar::new);

        avatar.setIdUsuario(idUsuario);
        avatar.setImagen(avatarDto.imagen());

        avatarRepository.save(avatar);
    }
}

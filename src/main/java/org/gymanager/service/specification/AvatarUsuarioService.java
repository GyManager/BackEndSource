package org.gymanager.service.specification;

import org.gymanager.model.client.AvatarDto;

public interface AvatarUsuarioService {

    String getAvatarUsuarioById(Long idUsuario);

    void updateAvatarUsuarioById(Long idUsuario, AvatarDto avatarDto);
}

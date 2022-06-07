package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.domain.usuarios.Usuario;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioEntityToDtoConverter implements GyManagerConverter<Usuario, UsuarioDto> {

    @NonNull
    private RolEntityToDtoConverter rolEntityToDtoConverter;

    @Override
    public UsuarioDto convert(Usuario source) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario(source.getIdUsuario());
        usuarioDto.setNombre(source.getNombre());
        usuarioDto.setFechaAlta(source.getFechaAlta());
        usuarioDto.setFechaBaja(source.getFechaBaja());
        usuarioDto.setMail(source.getMail());
        usuarioDto.setRoles(rolEntityToDtoConverter.convert(source.getRoles()));
        return usuarioDto;
    }
}

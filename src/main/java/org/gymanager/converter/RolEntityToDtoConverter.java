package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.usuarios.RolDto;
import org.gymanager.model.domain.usuarios.Rol;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RolEntityToDtoConverter implements GyManagerConverter<Rol, RolDto>{

    @NonNull
    private PermisoEntityToDtoConverter permisoEntityToDtoConverter;

    @Override
    public RolDto convert(Rol source) {
        RolDto rolDto = new RolDto();
        rolDto.setIdRol(source.getIdRol());
        rolDto.setNombreRol(source.getNombreRol());
        rolDto.setPermisos(permisoEntityToDtoConverter.convert(source.getPermisos()));
        return rolDto;
    }
}

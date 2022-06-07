package org.gymanager.converter;

import org.gymanager.model.client.usuarios.PermisoDto;
import org.gymanager.model.domain.usuarios.Permiso;
import org.springframework.stereotype.Component;

@Component
public class PermisoEntityToDtoConverter implements GyManagerConverter<Permiso, PermisoDto> {

    @Override
    public PermisoDto convert(Permiso source) {
        PermisoDto permisoDto = new PermisoDto();
        permisoDto.setIdPermiso(source.getIdPermiso());
        permisoDto.setNombrePermiso(source.getNombrePermiso());
        permisoDto.setObjeto(source.getObjeto());
        permisoDto.setDescripcion(source.getDescripcion());
        return permisoDto;
    }
}

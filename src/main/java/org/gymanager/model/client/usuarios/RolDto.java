package org.gymanager.model.client.usuarios;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolDto {

    private Long idRol;
    private String nombreRol;
    private List<PermisoDto> permisos;
}

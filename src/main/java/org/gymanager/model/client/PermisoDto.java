package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermisoDto {

    private Long idPermiso;
    private String nombrePermiso;
    private String objeto;
    private String descripcion;
}

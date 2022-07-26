package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EjercicioDto {

    private Long idEjercicio;

    private String nombre;

    private String tipoEjercicio;

    private String video;
}

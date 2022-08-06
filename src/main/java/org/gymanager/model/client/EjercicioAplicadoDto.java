package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EjercicioAplicadoDto {

    private Long idEjercicioAplicado;
    private EjercicioDto ejercicio;
    private String bloque;
    private Integer series;
    private Integer repeticiones;
    private String pausaMicro;
    private String pausaMacro;
    private String carga;
    private String tiempo;
    private Boolean esTemplate;
}

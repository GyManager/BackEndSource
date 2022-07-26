package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EjercicioDto {

    private Long idEjercicio;

    @NotBlank(message = "El nombre de ejercicio es obligatorio.")
    private String nombre;

    @NotBlank(message = "El tipo de ejercicio es obligatorio.")
    private String tipoEjercicio;

    private String video;
}

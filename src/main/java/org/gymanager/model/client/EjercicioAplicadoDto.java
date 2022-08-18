package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EjercicioAplicadoDto {

    private Long idEjercicioAplicado;

    @NotNull(message = "El ejercicio aplicado debe ser de un ejercicio existente")
    private Long idEjercicio;
    private String nombreEjercicio;
    private String tipoEjercicio;

    @NotBlank(message = "El bloque es obligatorio.")
    private String bloque;
    private Integer series;
    private Integer repeticiones;
    private String pausaMicro;
    private String pausaMacro;
    private String carga;
    private String tiempo;
    private Boolean esTemplate;
}

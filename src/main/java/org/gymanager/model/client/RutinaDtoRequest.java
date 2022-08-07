package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class RutinaDtoRequest {

    private Long idRutina;

    @NotBlank(message = "La rutina debe tener un nombre .")
    private String nombre;
    private Boolean esTemplate;

    @NotEmpty(message = "La rutina debe tener al menos un ejercicio")
    private List<EjercicioAplicadoDto> ejerciciosAplicados;
}

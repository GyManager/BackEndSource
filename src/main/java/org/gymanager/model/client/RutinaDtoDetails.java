package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class RutinaDtoDetails extends RutinaDto {

    @NotEmpty(message = "La rutina debe tener al menos un ejercicio")
    private List<EjercicioAplicadoDto> ejerciciosAplicados;
}

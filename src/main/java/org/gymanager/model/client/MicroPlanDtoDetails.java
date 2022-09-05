package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class MicroPlanDtoDetails extends MicroPlanDto{

    @NotEmpty(message = "El micro plan debe tener al menos una rutina")
    @Size(max=7, message = "El micro plan no puede tener mas de 7 rutinas")
    @Valid
    private List<RutinaDtoDetails> rutinas;

    private List<ObservacionDto> observaciones;
}

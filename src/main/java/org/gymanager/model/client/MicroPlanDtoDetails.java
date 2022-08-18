package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class MicroPlanDtoDetails extends MicroPlanDto{

    @NotEmpty(message = "El micro plan debe tener al menos una rutina")
    private List<RutinaDtoDetails> rutinas;
}

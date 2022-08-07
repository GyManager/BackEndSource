package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class MicroPlanDtoRequest {

    private Long idMicroPlan;

    @NotBlank(message = "El micro plan debe tener un nombre .")
    private String nombre;
    private Boolean esTemplate;
    private Integer numeroOrden;

    @NotEmpty(message = "El micro plan debe tener al menos una rutina")
    private List<RutinaDtoRequest> rutinas;
}

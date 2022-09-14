package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
public class MicroPlanDto {

    private Long idMicroPlan;

    @NotBlank(message = "El micro plan debe tener un nombre .")
    private String nombre;
    private Boolean esTemplate;
    private Integer numeroOrden;
    private Integer cantidadRutinas;

    public void setNombre(String nombre) {
        this.nombre = Objects.isNull(nombre) ? null : nombre.trim();
    }
}

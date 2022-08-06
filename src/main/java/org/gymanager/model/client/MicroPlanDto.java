package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MicroPlanDto {

    private Long idMicroPlan;
    private String nombre;
    private Boolean esTemplate;
    private Integer numeroOrden;
}

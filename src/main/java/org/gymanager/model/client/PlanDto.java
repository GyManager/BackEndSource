package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlanDto {

    private Long idPlan;
    private Long idCliente;
    private Long idUsuarioProfesor;
    private String objetivo;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String descripcion;
}

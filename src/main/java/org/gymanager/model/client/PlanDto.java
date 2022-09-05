package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class PlanDto {

    private Long idPlan;
    private Long idCliente;
    private Long idUsuarioProfesor;

    @NotBlank(message = "El plan debe tener indicado que profesor lo esta creando")
    private String usuarioProfesor;

    @NotBlank(message = "El plan debe tener un objetivo seleccionado")
    private String objetivo;

    @NotNull(message = "El plan debe tener una fecha de inicio")
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    @NotBlank(message = "El plan debe tener una descripcion")
    private String descripcion;
}

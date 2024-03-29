package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;

    @NotBlank(message = "El plan debe tener una descripcion")
    private String descripcion;

    private String observacionCliente;
    private EstadoSeguimientoDto estadoSeguimientoDto;

    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.isNull(descripcion) ? null : descripcion.trim();
    }
}

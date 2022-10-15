package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class MatriculaDto {

    private Long idMatricula;
    private Long idCliente;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDateTime fechaPago;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaVencimiento;

    @NotNull(message = "La cantidad de meses es obligatoria")
    @Min(value = 1, message = "El numero de meses que dura esta matricula debe ser 1 o mas")
    @Max(value = 12_000, message = "El numero de meses que dura esta matricula no puede ser mas de 12.000")
    private Integer cantidadMeses;

    @NotNull(message = "La cantidad de dias a la semana es obligatoria")
    @Min(value = 1, message = "El numero de dias a la semana debe ser 1 o mas")
    @Max(value = 7, message = "El numero de dias a la semana debe ser 7 o menos")
    private Integer cantidadDiasSemana;
    private String matriculaEstado;
}

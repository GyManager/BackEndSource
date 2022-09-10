package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MatriculaDto {

    private Long idMatricula;
    private Long idCliente;
    private LocalDate fechaPago;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Integer cantidadMeses;
    private Integer cantidadDiasSemana;
    private String matriculaEstado;
}

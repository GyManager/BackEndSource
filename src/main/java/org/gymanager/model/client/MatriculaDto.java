package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MatriculaDto {

    private Long idMatricula;
    private Long idCliente;
    private LocalDateTime fechaPago;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaVencimiento;
    private Integer cantidadMeses;
    private Integer cantidadDiasSemana;
    private String matriculaEstado;
}

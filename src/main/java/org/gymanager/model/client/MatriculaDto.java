package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatriculaDto {

    private Long idMatricula;
    private Long idCliente;
    private Date fechaPago;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private Integer cantidadMeses;
    private Integer cantidadDiasSemana;
}

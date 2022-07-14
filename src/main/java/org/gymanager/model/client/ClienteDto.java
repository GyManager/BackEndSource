package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClienteDto {

    private Long idCliente;
    private UsuarioDto usuario;
    private String objetivo;
    private String direccion;
    private Date fechaNacimiento;
    private String observaciones;
}

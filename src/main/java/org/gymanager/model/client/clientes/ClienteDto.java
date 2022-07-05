package org.gymanager.model.client.clientes;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClienteDto {

    private Long idPersona;
    private Long idUsuario;
    private String mail;
    private Long numeroDocumento;
    private Long idTipoDocumento;
    private String tipoDocumento;
    private String nombre;
    private String apellido;
    private String direccion;
    private Date fechaNacimiento;
    private String celular;
    private String objetivo;
}

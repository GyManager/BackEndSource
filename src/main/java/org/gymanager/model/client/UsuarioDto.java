package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDto {

    private Long idUsuario;
    private Long numeroDocumento;
    private String tipoDocumento;
    private String nombre;
    private String apellido;
    private String sexo;
    private String mail;
    private Long celular;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
}

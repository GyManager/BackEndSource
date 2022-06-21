package org.gymanager.model.client.usuarios;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDto {

    private Long idUsuario;
    private String nombre;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private String mail;
}

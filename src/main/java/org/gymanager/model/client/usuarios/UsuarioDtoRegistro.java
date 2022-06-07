package org.gymanager.model.client.usuarios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDtoRegistro {
    private String nombre;
    private String pass;
    private String mail;
}

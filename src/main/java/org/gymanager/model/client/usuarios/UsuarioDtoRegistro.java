package org.gymanager.model.client.usuarios;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioDtoRegistro {
    @NotBlank(message = "El nombre del usuario es obligatorio")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    private String pass;

    @NotBlank(message = "El mail es obligatorio")
    private String mail;
}

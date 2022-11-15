package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioRecovery {

    @NotBlank(message = "El mail es obligatorio.")
    @Email(message = "El mail ingresado debe ser un mail correctamente formado.")
    private String mail;

    public void setMail(String mail) {
        this.mail = mail.trim();
    }
}

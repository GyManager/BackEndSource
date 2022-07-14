package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsuarioDtoRegistro {
    @NotBlank(message = "El nombre del usuario es obligatorio.")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, max = 25, message = "La contraseña debe tener entre 8 y 25 caracteres.")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*#?&]+",
            message = "La contraseña debe contener al menos un numero, una mayuscula y una minuscula")
    private String pass;

    @NotBlank(message = "La confirmacion de la contraseña es obligatoria")
    private String confirmacionPass;

    @NotBlank(message = "El mail es obligatorio.")
    @Email(message = "El mail ingresado debe ser un mail correctamente formado.")
    private String mail;
}

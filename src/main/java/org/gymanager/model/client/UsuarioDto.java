package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDto {

    private Long idUsuario;

    @Min(value = 1L, message = "El numero de documento es obligatorio.")
    private Long numeroDocumento;

    @NotBlank(message = "El tipo de documento es obligatorio.")
    private String tipoDocumento;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre debe tenes menos de 50 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(max = 50, message = "El apellido debe tenes menos de 50 caracteres.")
    private String apellido;

    private String sexo;

    @NotBlank(message = "El mail es obligatorio.")
    @Email(message = "El mail ingresado debe ser un mail correctamente formado.")
    private String mail;
    private Long celular;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
}

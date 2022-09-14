package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;
import org.gymanager.model.enums.ClienteEstado;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
public class ClienteDto {

    private Long idCliente;

    @Valid
    @NotNull(message = "Error faltan multiples datos del cliente")
    private UsuarioDto usuario;

    @NotBlank(message = "El objetivo es obligatorio.")
    private String objetivo;

    private String direccion;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento no puede ser una fecha futura")
    private Date fechaNacimiento;

    private String observaciones;
    private ClienteEstado clienteEstado;

    public void setDireccion(String direccion) {
        this.direccion = direccion.trim();
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones.trim();
    }
}

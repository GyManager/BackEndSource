package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RutinaDto {

    private Long idRutina;

    @NotBlank(message = "La rutina debe tener un nombre .")
    private String nombre;
    private Boolean esTemplate;

    public void setNombre(String nombre) {
        this.nombre = nombre.trim();
    }
}

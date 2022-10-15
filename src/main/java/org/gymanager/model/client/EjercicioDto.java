package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class EjercicioDto {

    private Long idEjercicio;

    private String nombre;

    private String tipoEjercicio;

    private String video;

    public void setNombre(String nombre) {
        this.nombre = Objects.isNull(nombre) ? null : nombre.trim();
    }

    public void setVideo(String video) {
        this.video = Objects.isNull(video) ? null : video.trim();
    }
}

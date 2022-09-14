package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class EjercicioDtoRequest {

    @NotBlank(message = "El nombre del ejercicio es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de ejercicio es obligatorio")
    private String tipoEjercicio;

    private String video;

    @Valid
    private List<PasoDto> pasos;

    private List<Long> idHerramientaList;

    public void setNombre(String nombre) {
        this.nombre = Objects.isNull(nombre) ? null : nombre.trim();

    }

    public void setVideo(String video) {
        this.video = Objects.isNull(video) ? null : video.trim();
    }
}

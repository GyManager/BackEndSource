package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.Objects;

@Getter
@Setter
public class PasoDto {

    private Long idPaso;

    @Min(value = 1, message = "El numero de paso debe ser 1 o mas")
    private Integer numeroPaso;

    private String contenido;

    private String imagen;

    public void setContenido(String contenido) {
        this.contenido = Objects.isNull(contenido) ? null : contenido.trim();
    }
}

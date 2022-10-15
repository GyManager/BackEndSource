package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ObservacionDto {

    private Long idObservacion;
    private Integer numeroSemana;
    private String observacion;

    public void setObservacion(String observacion) {
        if(Objects.isNull(observacion)){
            this.observacion = observacion;
        } else {
            this.observacion = observacion.trim();
        }
    }
}

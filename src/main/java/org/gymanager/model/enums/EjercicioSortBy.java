package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum EjercicioSortBy {
    NONE(""),
    NOMBRE("nombre"),
    TIPO_EJERCICIO("tipoEjercicio.nombre");

    private final String field;

    EjercicioSortBy(String field) {
        this.field = field;
    }
}

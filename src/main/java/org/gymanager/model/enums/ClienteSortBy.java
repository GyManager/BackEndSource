package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum ClienteSortBy {
    NONE(""),
    NUMERO_DOCUMENTO("usuario.numeroDocumento"),
    NOMBRE("usuario.nombre"),
    APELLIDO("usuario.apellido"),
    MAIL("usuario.mail");

    private final String field;

    ClienteSortBy(String field) {
        this.field = field;
    }
}

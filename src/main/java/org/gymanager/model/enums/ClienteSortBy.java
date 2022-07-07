package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum ClienteSortBy {
    NONE(""),
    NUMERO_DOCUMENTO("numeroDocumento"),
    NOMBRE("nombre"),
    APELLIDO("apellido"),
    MAIL("usuario.mail");

    private final String field;

    ClienteSortBy(String field) {
        this.field = field;
    }
}

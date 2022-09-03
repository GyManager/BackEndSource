package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum UsuarioSortBy {
    NONE(""),
    NUMERO_DOCUMENTO("numeroDocumento"),
    NOMBRE("nombre"),
    APELLIDO("apellido"),
    MAIL("mail");

    private final String field;

    UsuarioSortBy(String field) {
        this.field = field;
    }
}

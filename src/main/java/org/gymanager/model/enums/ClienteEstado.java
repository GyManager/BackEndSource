package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum ClienteEstado {
    NO_MATRICULADO("NO MATRICULADO"),
    PRONTO_A_VENCER("PRONTO A VENCER"),
    MATRICULADO("MATRICULADO"),
    DESACTIVADO("DESACTIVADO");

    private final String estado;

    ClienteEstado(String estado) {
        this.estado = estado;
    }

}

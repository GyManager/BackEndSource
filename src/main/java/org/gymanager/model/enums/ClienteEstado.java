package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum ClienteEstado {
    NO_MATRICULADO("No matriculado"),
    PRONTO_A_VENCER("Pronto a vencer"),
    MATRICULADO("Matriculado"),
    DESACTIVADO("Desactivado");

    private final String estado;

    ClienteEstado(String estado) {
        this.estado = estado;
    }

}

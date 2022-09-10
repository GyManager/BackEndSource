package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum MatriculaEstado {
    NO_INICIADA("NO INICIADA"),
    ACTIVA("ACTIVA"),
    PRONTO_A_VENCER("PRONTO A VENCER"),
    VENCIDA("VENCIDA");

    private final String estado;

    MatriculaEstado(String estado) {
        this.estado = estado;
    }
}

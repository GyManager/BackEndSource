package org.gymanager.utils;

import lombok.Getter;

@Getter
public enum Permisos {
    GET_PLANES("get-planes"),
    GET_MICRO_PLANES("get-micro-planes"),
    GET_MATRICULAS("get-matriculas");

    private final String name;

    Permisos(String name) {
        this.name = name;
    }
}

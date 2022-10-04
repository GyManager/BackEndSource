package org.gymanager.utils;

import lombok.Getter;

@Getter
public enum Permisos {
    GET_PLANES("get-planes");

    private final String name;

    Permisos(String name) {
        this.name = name;
    }
}

package org.gymanager.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum HeadersReporteClientes {
    AVATAR("Avatar"),
    NOMBRE("Nombre"),
    APELLIDO("Apellido"),
    EMAIL("Email");

    private final String valor;

    public static List<HeadersReporteClientes> getOrderedList(){
        return List.of(AVATAR, NOMBRE, APELLIDO, EMAIL);
    }
}

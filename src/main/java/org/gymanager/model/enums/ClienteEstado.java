package org.gymanager.model.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.function.Predicate;

@Getter
public enum ClienteEstado {
    NO_MATRICULADO("NO MATRICULADO", num -> Objects.isNull(num) || num < 0),
    PRONTO_VENCE("PRONTO A VENCER", num -> num >= 0 && num <= 7),
    ACTIVO("ACTIVO", num -> num > 7);

    private final String estado;
    private final Predicate<Integer> validate;

    ClienteEstado(String estado, Predicate<Integer> validate) {
        this.estado = estado;
        this.validate = validate;
    }

    public static ClienteEstado getEstadoSegunVencimiento(Integer diasHastaVencimiento) {
        if(NO_MATRICULADO.getValidate().test(diasHastaVencimiento)) {
            return NO_MATRICULADO;
        } else if(PRONTO_VENCE.getValidate().test(diasHastaVencimiento)) {
            return PRONTO_VENCE;
        } else {
            return ACTIVO;
        }
    }
}

package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum MicroPlanSortBy {
    NONE(""),
    NOMBRE("nombre");

    private final String field;

    MicroPlanSortBy(String field) {
        this.field = field;
    }
}

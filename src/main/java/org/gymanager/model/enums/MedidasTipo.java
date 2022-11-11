package org.gymanager.model.enums;

import lombok.Getter;

@Getter
public enum MedidasTipo {
    PESO("peso", "Peso"),
    CERVICAL("cervical", "Cervical"),
    LUMBAR("lumbar", "Lumbar"),
    COXAL_PELVICA("coxalPelvica", "Coxal Pelvica"),
    CADERA("cadera", "Cadera"),
    MUSLOS_IZQ("muslosIzq", "Muslos Izquierda"),
    MUSLOS_DER("muslosDer", "Muslos Derecha"),
    RODILLAS_IZQ("rodillasIzq", "Rodillas Izquierda"),
    RODILLAS_DER("rodillasDer", "Rodillas Derecha"),
    GEMELOS_IZQ("gemelosIzq", "Gemelos Izquierda"),
    GEMELOS_DER("gemelosDer", "Gemelos Derecha"),
    BRAZO_IZQ("brazoIzq", "Brazo Izquierda"),
    BRAZO_DEr("brazoDer", "Brazo Derecha");

    private final String alias;
    private final String name;

    MedidasTipo(String alias, String name) {
        this.alias = alias;
        this.name = name;
    }

}

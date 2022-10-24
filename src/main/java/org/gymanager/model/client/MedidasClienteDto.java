package org.gymanager.model.client;

import java.time.LocalDate;

public record MedidasClienteDto(
        Long idMedidas,
        LocalDate fecha,
        Float peso,
        Float altura,
        Float cervical,
        Float dorsal,
        Float lumbar,
        Float coxalPelvica,
        Float cadera,
        Float muslosIzq,
        Float muslosDer,
        Float rodillasIzq,
        Float rodillasDer,
        Float gemelosIzq,
        Float gemelosDer,
        Float brazoIzq,
        Float brazoDer,
        String foto) {
}

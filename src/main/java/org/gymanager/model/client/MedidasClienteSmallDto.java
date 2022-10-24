package org.gymanager.model.client;

import java.time.LocalDate;

public record MedidasClienteSmallDto(
        Long idMedidas,
        LocalDate fecha) {
}

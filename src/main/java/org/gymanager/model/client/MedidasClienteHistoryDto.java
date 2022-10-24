package org.gymanager.model.client;

import java.time.LocalDate;

public record MedidasClienteHistoryDto(LocalDate fecha,
                                       Float valor) {
}

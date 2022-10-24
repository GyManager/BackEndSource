package org.gymanager.model.client;

import java.util.List;

public record MedidasClienteSummaryDto(String tipo,
                                       List<MedidasClienteHistoryDto> medidasClienteHistory,
                                       Double average,
                                       Long count,
                                       Double min,
                                       Double max) {
}

package org.gymanager.model.client;

import java.time.LocalDateTime;

public record ClientePlanResumenDto(Long idCliente, String objetivo, String observaciones,
                                    LocalDateTime fechaHastaPlanVigente, Long idPlanVigente, Integer cantidadDiasSemana){
}

package org.gymanager.model.client;

import java.time.LocalDate;

public record ClientePlanResumenDto(Long idCliente, String objetivo, String observaciones,
                                    LocalDate fechaHastaPlanVigente, Long idPlanVigente, Integer cantidadDiasSemana){
}

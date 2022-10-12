package org.gymanager.model.client;

import java.time.LocalDate;

public record SeguimientoEjercicioDto(Long idSeguimientoEjercicio,
                                      LocalDate fechaCarga,
                                      Long idPlan,
                                      Long idEjercicioAplicado,
                                      Float cargaReal,
                                      Float tiempoReal,
                                      String observacion){
}

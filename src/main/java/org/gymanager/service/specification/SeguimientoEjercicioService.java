package org.gymanager.service.specification;

import org.gymanager.model.client.SeguimientoEjercicioRequestDto;

public interface SeguimientoEjercicioService {

    Long addSeguimientoEjercicio(Long idPlan, Long idEjercicioAplicado,
                                 SeguimientoEjercicioRequestDto seguimientoEjercicioRequestDto);
}

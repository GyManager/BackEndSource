package org.gymanager.service.specification;

import org.gymanager.model.client.SeguimientoFinDiaDto;

public interface SeguimientoService {

    Long addSeguimientoEjercicio(Long idPlan, Long idMicroPlan, Long idRutina, SeguimientoFinDiaDto seguimientoFinDiaDto);
}

package org.gymanager.service.specification;

import org.gymanager.model.client.SeguimientoFinDiaDto;
import org.gymanager.model.client.SeguimientoFinDiaDtoDetail;
import org.gymanager.model.enums.SeguimientosFilter;

import java.util.List;

public interface SeguimientoService {

    Long addSeguimientoEjercicio(Long idPlan, Long idMicroPlan, Long idRutina, SeguimientoFinDiaDto seguimientoFinDiaDto);

    List<SeguimientoFinDiaDtoDetail> getSeguimientoFinDiaByIdMicroPlan(Long idPlan, Long idMicroPlan, SeguimientosFilter seguimientosFilter);
}

package org.gymanager.service.specification;

import org.gymanager.model.client.SeguimientoEjercicioDto;
import org.gymanager.model.client.SeguimientoEjercicioRequestDto;
import org.gymanager.model.enums.SeguimientosFilter;

import java.util.List;

public interface SeguimientoEjercicioService {

    Long addSeguimientoEjercicio(Long idPlan, Long idEjercicioAplicado,
                                 SeguimientoEjercicioRequestDto seguimientoEjercicioRequestDto);

    List<SeguimientoEjercicioDto> getSeguimientoEjercicioByIdRutina(Long idPlan,
                                                                    Long idMicroPlan,
                                                                    Long idRutina,
                                                                    SeguimientosFilter seguimientosFilter);
}

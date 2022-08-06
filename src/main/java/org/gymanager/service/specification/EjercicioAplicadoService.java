package org.gymanager.service.specification;

import org.gymanager.model.client.EjercicioAplicadoDto;

import java.util.List;

public interface EjercicioAplicadoService {

    List<EjercicioAplicadoDto> getEjerciciosAplicadosByIdRutina(Long idMicroPlan, Long idRutina);
}

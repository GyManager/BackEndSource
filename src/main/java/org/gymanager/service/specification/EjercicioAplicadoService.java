package org.gymanager.service.specification;

import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.model.domain.EjercicioAplicado;

import java.util.List;

public interface EjercicioAplicadoService {

    List<EjercicioAplicadoDto> getEjerciciosAplicadosByIdRutina(Long idMicroPlan, Long idRutina);

    List<EjercicioAplicado> crearEjerciciosAplicados(List<EjercicioAplicadoDto> ejerciciosAplicados);
}

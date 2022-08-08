package org.gymanager.service.specification;

import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.model.domain.EjercicioAplicado;
import org.gymanager.model.domain.Rutina;

import java.util.List;

public interface EjercicioAplicadoService {

    List<EjercicioAplicadoDto> getEjerciciosAplicadosByIdRutina(Long idMicroPlan, Long idRutina);

    List<EjercicioAplicado> crearEjerciciosAplicados(List<EjercicioAplicadoDto> ejerciciosAplicados);

    void actualizarEjerciciosAplicadosRutina(List<EjercicioAplicadoDto> ejerciciosAplicados, Rutina rutina);
}

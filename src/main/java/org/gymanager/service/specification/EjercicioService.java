package org.gymanager.service.specification;

import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.domain.Ejercicio;

import java.util.List;

public interface EjercicioService {

    List<EjercicioDto> getEjercicios();

    EjercicioDto getEjercicioById(Long idEjercicio);

    Ejercicio getEjercicioEntityById(Long idEjercicio);
}

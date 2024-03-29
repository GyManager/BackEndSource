package org.gymanager.service.specification;

import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.EjercicioDtoRequest;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EjercicioService {

    GyManagerPage<EjercicioDto> getEjercicios(String search, Boolean excluirEliminados, Integer page, Integer pageSize,
                                              EjercicioSortBy sortBy, Sort.Direction direction);

    EjercicioDto getEjercicioById(Long idEjercicio);

    Ejercicio getEjercicioEntityById(Long idEjercicio);

    List<EjercicioDto> getEjerciciosByIdIn(List<Long> idEjercicioList);

    List<Ejercicio> getEjerciciosEntityByIdIn(List<Long> idEjercicioList);

    Long addEjercicio(EjercicioDtoRequest ejercicioDtoRequest);

    void updateEjercicioById(Long idEjercicio, EjercicioDtoRequest ejercicioDtoRequest);

    void deleteEjercicioById(Long idEjercicio);
}

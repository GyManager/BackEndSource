package org.gymanager.service.specification;

import org.gymanager.model.domain.TipoEjercicio;

import java.util.List;

public interface TipoEjercicioService {

    TipoEjercicio getTipoEjercicioByNombre(String nombre);

    List<TipoEjercicio> getTipoEjercicios();
}

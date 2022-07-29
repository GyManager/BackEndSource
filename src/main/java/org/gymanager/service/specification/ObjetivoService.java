package org.gymanager.service.specification;

import org.gymanager.model.domain.Objetivo;

import java.util.List;

public interface ObjetivoService {
    Objetivo getObjetivoByObjetivo(String objetivo);

    List<Objetivo> getObjetivos();
}

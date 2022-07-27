package org.gymanager.service.specification;

import org.gymanager.model.client.HerramientaDto;
import org.gymanager.model.domain.Herramienta;

import java.util.List;

public interface HerramientaService {
    List<HerramientaDto> getHerramientasByIdEjercicio(Long idEjercicio);

    List<Herramienta> getHerramientasByIds(List<Long> idHerramientaList);
}

package org.gymanager.service.specification;

import org.gymanager.model.client.HerramientaDto;

import java.util.List;

public interface HerramientaService {
    List<HerramientaDto> getHerramientasByIdEjercicio(Long idEjercicio);
}

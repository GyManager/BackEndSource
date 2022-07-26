package org.gymanager.service.specification;

import org.gymanager.model.client.PasoDto;

import java.util.List;

public interface PasoService {
    List<PasoDto> getPasosByIdEjercicio(Long idEjercicio);
}

package org.gymanager.service.specification;

import org.gymanager.model.client.PasoDto;
import org.gymanager.model.domain.Paso;

import java.util.List;

public interface PasoService {
    List<PasoDto> getPasosByIdEjercicio(Long idEjercicio);

    List<Paso> crearPasos(List<PasoDto> pasoDto);
}

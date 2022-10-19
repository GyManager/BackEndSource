package org.gymanager.service.specification;

import org.gymanager.model.client.EstadoSeguimientoDto;

import java.util.List;

public interface ParameterService {

    List<String> getTipoEjercicios();

    List<String> getObjetivos();

    List<String> getSexos();

    List<String> getTipoDocumentos();

    List<String> getBloques();

    List<EstadoSeguimientoDto> getEstadoSeguimientos();
}

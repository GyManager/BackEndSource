package org.gymanager.service.specification;

import org.gymanager.model.client.MatriculaDto;

import java.util.List;

public interface MatriculaService {

    List<MatriculaDto> getMatriculasByIdCliente(Long idCliente, Boolean last);

    Long addMatricula(Long idCliente, MatriculaDto matriculaDto);
}

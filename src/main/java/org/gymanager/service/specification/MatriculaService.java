package org.gymanager.service.specification;

import org.gymanager.model.client.MatriculaDto;
import org.gymanager.model.domain.Matricula;
import org.gymanager.model.enums.MatriculasFilter;

import java.util.List;

public interface MatriculaService {

    List<MatriculaDto> getMatriculasByIdCliente(Long idCliente, MatriculasFilter matriculasFilter, Boolean validateUser);

    List<Matricula> getMatriculasEntitiesByIdCliente(Long idCliente, MatriculasFilter matriculasFilter);

    Long addMatricula(Long idCliente, MatriculaDto matriculaDto);

    Matricula getMatriculaEntityById(Long idMatricula);

    void deleteMatriculaById(Long idCliente, Long idMatricula);
}

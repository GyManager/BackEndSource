package org.gymanager.service.specification;

import org.gymanager.model.domain.Sexo;

import java.util.List;

public interface SexoService {
    Sexo getSexoByNombreSexo(String nombreSexo);

    List<Sexo> getSexos();
}

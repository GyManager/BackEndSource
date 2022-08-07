package org.gymanager.service.specification;

import org.gymanager.model.domain.Bloque;

import java.util.List;

public interface BloqueService {
    Bloque getBloqueByNombre(String nombre);

    List<Bloque> getBloques();
}

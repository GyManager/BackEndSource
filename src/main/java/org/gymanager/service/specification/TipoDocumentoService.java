package org.gymanager.service.specification;

import org.gymanager.model.domain.TipoDocumento;

import java.util.List;

public interface TipoDocumentoService {

    TipoDocumento getTipoDocumentoByTipo(String tipo);

    List<TipoDocumento> getTipoDocumentos();
}

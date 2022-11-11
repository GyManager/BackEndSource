package org.gymanager.handler.specification;

import org.gymanager.model.enums.ClienteSortBy;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportServiceHandler {
    void exportClientes(String fuzzySearch, ClienteSortBy sortBy, Sort.Direction direction, Long matriculaVenceEn,
                        Long matriculaVenceEnOverdue, Long sinFinalizarRutinaEn, HttpServletResponse response);

    void exportClientesByUltimosSeguimientos(String fuzzySearch, ClienteSortBy sortBy, Sort.Direction direction,
                                               Long cantidadDias, List<Long> idEstadoSeguimientoList,
                                               HttpServletResponse response);
}

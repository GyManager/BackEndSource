package org.gymanager.handler.specification;

import org.gymanager.model.enums.ClienteSortBy;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletResponse;

public interface ExportServiceHandler {
    void exportClientes(String fuzzySearch, ClienteSortBy sortBy, Sort.Direction direction, Long matriculaVenceEn,
                        Long matriculaVenceEnOverdue, Long sinFinalizarRutinaEn, HttpServletResponse response);
}

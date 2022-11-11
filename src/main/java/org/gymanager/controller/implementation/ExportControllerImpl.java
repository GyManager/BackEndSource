package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.ExportController;
import org.gymanager.handler.specification.ExportServiceHandler;
import org.gymanager.model.enums.ClienteSortBy;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExportControllerImpl implements ExportController {

    @NonNull
    private ExportServiceHandler exportServiceHandler;

    @Override
    public void getClientes(
            String fuzzySearch, ClienteSortBy sortBy, Sort.Direction direction, Long matriculaVenceEn,
            Long matriculaVenceEnOverdue, Long sinFinalizarRutinaEn, HttpServletResponse response) {

        response.setContentType("application/pdf");
        var currentDateTime = LocalDateTime.now();
        var headerKey = "Content-Disposition";
        var headerValue = "attachment; filename=clientes_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        exportServiceHandler.exportClientes(
                fuzzySearch,
                sortBy,
                direction,
                matriculaVenceEn,
                matriculaVenceEnOverdue,
                sinFinalizarRutinaEn,
                response
        );


    }

    @Override
    public void getClientesByUltimosSeguimientos(
            String fuzzySearch, ClienteSortBy sortBy, Sort.Direction direction,Long cantidadDias,
            List<Long> idEstadoSeguimientoList, HttpServletResponse response) {

        response.setContentType("application/pdf");
        var currentDateTime = LocalDateTime.now();
        var headerKey = "Content-Disposition";
        var headerValue = "attachment; filename=clientes_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        exportServiceHandler.exportClientesByUltimosSeguimientos(
                fuzzySearch,
                sortBy,
                direction,
                cantidadDias,
                idEstadoSeguimientoList,
                response
        );
    }
}

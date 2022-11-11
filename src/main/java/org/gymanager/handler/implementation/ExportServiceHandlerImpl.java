package org.gymanager.handler.implementation;

import com.itextpdf.text.DocumentException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.handler.specification.ExportServiceHandler;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.enums.HeadersReporteClientes;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.ExportService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceHandlerImpl implements ExportServiceHandler {

    private static final Integer PAGE = 0;
    private static final Integer PAGE_SIZE = Integer.MAX_VALUE;
    private static final String CLIENTES_VENCIMIENTO_TITLE = "Clientes con vencimiento de matricula pronto";
    private static final String CLIENTES_SIN_FINALIZAR = "Clientes matriculados sin finalizar rutina en los ultimos dias";

    @NonNull
    private ClienteService clienteService;

    @NonNull
    private ExportService exportService;

    @Override
    public void exportClientes(String fuzzySearch, ClienteSortBy sortBy, Sort.Direction direction, Long matriculaVenceEn,
                               Long matriculaVenceEnOverdue, Long sinFinalizarRutinaEn, HttpServletResponse response) {

        var clientes = clienteService.getClientes(
                fuzzySearch,
                PAGE,
                PAGE_SIZE,
                sortBy,
                direction,
                matriculaVenceEn,
                matriculaVenceEnOverdue,
                sinFinalizarRutinaEn);

        var tableData = clientes.getContent()
                .stream()
                .map(clienteDto -> List.of(clienteDto.getUsuario().getNombre(),
                        clienteDto.getUsuario().getApellido(),
                        clienteDto.getUsuario().getMail()))
                .toList();

        var headers = HeadersReporteClientes.getOrderedList()
                .stream()
                .map(HeadersReporteClientes::getValor)
                .toList();

        var title = Objects.isNull(sinFinalizarRutinaEn) ? CLIENTES_VENCIMIENTO_TITLE : CLIENTES_SIN_FINALIZAR;

        try {
            exportService.export(response.getOutputStream(), tableData, title, headers);
        } catch (DocumentException | IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Problema procesando el reporte");
        }
    }
}

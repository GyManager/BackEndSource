package org.gymanager.handler.specification;

import org.gymanager.model.client.ClienteUltimosSeguimientosDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ClienteServiceHandler {
    GyManagerPage<ClienteUltimosSeguimientosDto> getClientesByUltimosSeguimientos(
            String fuzzySearch, Integer page, Integer pageSize, ClienteSortBy sortBy, Sort.Direction direction,
            Long cantidadDias, List<Long> idEstadoSeguimientoList);
}

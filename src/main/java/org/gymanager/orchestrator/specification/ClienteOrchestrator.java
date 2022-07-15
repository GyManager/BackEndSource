package org.gymanager.orchestrator.specification;

import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

public interface ClienteOrchestrator {

    GyManagerPage<ClienteDto> getClientes(String fuzzySearch, Integer page, Integer pageSize, ClienteSortBy sortBy,
                                          Sort.Direction direction);

    ClienteDto getClientesById(Long idCliente);
}

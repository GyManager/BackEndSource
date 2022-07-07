package org.gymanager.service.specification;

import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

public interface ClienteService {

    GyManagerPage<ClienteDto> getClientes(String fuzzySearch, Integer page, Integer pageSize, ClienteSortBy sortBy,
                                          Sort.Direction direction);

    ClienteDto getClientesById(Long idCliente);
}

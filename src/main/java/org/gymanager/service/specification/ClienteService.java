package org.gymanager.service.specification;

import org.gymanager.model.domain.Cliente;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

public interface ClienteService {

    GyManagerPage<Cliente> getClientes(String fuzzySearch, Integer page, Integer pageSize, ClienteSortBy sortBy,
                                       Sort.Direction direction);

    Cliente getClientesById(Long idCliente);
}

package org.gymanager.service.specification;

import org.gymanager.model.client.clientes.ClienteDto;

import java.util.List;

public interface ClienteService {

    List<ClienteDto> getClientes();

    ClienteDto getClientesById(Long idCliente);
}

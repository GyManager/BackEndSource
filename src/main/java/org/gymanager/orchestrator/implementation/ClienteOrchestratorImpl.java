package org.gymanager.orchestrator.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.orchestrator.specification.ClienteOrchestrator;
import org.gymanager.service.specification.ClienteService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteOrchestratorImpl implements ClienteOrchestrator {


    @NonNull
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @NonNull
    private ClienteService clienteService;


    @Override
    public GyManagerPage<ClienteDto> getClientes(String fuzzySearch, Integer page, Integer pageSize,
                                                 ClienteSortBy sortBy, Sort.Direction direction) {
        return clienteService.getClientes(fuzzySearch, page, pageSize, sortBy, direction)
                .map(clienteEntityToDtoConverter::convert);
    }

    @Override
    public ClienteDto getClientesById(Long idCliente) {
        return clienteEntityToDtoConverter.convert(clienteService.getClientesById(idCliente));
    }
}

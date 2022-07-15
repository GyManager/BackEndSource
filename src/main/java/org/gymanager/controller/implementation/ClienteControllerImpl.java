package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.ClienteController;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.orchestrator.specification.ClienteOrchestrator;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClienteControllerImpl implements ClienteController {

    @NonNull
    private ClienteOrchestrator clienteOrchestrator;

    @Override
    public ResponseEntity<GyManagerPage<ClienteDto>> getClientes(String fuzzySearch, Integer page, Integer pageSize,
                                                                 ClienteSortBy sortBy, Sort.Direction direction) {
        return ResponseEntity.ok(clienteOrchestrator.getClientes(fuzzySearch, page, pageSize, sortBy, direction));
    }

    @Override
    public ResponseEntity<ClienteDto> getClienteById(Long idCliente) {
        return ResponseEntity.ok(clienteOrchestrator.getClientesById(idCliente));
    }
}

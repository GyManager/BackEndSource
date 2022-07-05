package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.ClienteController;
import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.service.specification.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClienteControllerImpl implements ClienteController {

    @NonNull
    private ClienteService clienteService;

    @Override
    public ResponseEntity<List<ClienteDto>> getClientes() {
        return ResponseEntity.ok(clienteService.getClientes());
    }

    @Override
    public ResponseEntity<ClienteDto> getClienteById(Long idCliente) {
        return ResponseEntity.ok(clienteService.getClientesById(idCliente));
    }
}

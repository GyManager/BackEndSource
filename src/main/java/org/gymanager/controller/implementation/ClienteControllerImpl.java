package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.ClienteController;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.utils.Permisos;
import org.gymanager.utils.UserPermissionValidation;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClienteControllerImpl implements ClienteController {

    @NonNull
    private ClienteService clienteService;

    @Override
    public ResponseEntity<GyManagerPage<ClienteDto>> getClientes(String fuzzySearch, Integer page, Integer pageSize,
                                                                 ClienteSortBy sortBy, Sort.Direction direction,
                                                                 Long matriculaVenceEn, Long matriculaVenceEnOverdue,
                                                                 Long sinFinalizarRutinaEn) {
        return ResponseEntity.ok(clienteService.getClientes(
                fuzzySearch,
                page,
                pageSize,
                sortBy,
                direction,
                matriculaVenceEn,
                matriculaVenceEnOverdue,
                sinFinalizarRutinaEn
        ));
    }

    @Override
    public ResponseEntity<ClienteDto> getClienteById(Long idCliente) {
        return ResponseEntity.ok(clienteService.getClientesById(idCliente));
    }

    @Override
    public ResponseEntity<Long> addCliente(ClienteDto clienteDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteService.addCliente(clienteDto));
    }

    @Override
    public ResponseEntity<Void> updateClienteById(Long idCliente, Boolean reactivate, ClienteDto clienteDto) {
        var validateUser = !UserPermissionValidation.userHasPermission(
                SecurityContextHolder.getContext().getAuthentication(),
                Permisos.PUT_CLIENTES);

        clienteService.updateClienteById(idCliente, clienteDto, validateUser, reactivate && !validateUser);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteClienteById(Long idCliente) {
        clienteService.deleteClienteById(idCliente);
        return ResponseEntity.noContent().build();
    }
}

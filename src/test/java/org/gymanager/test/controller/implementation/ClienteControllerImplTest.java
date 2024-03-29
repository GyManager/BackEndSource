package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.ClienteControllerImpl;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_CLIENTE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerImplTest {

    @InjectMocks
    private ClienteControllerImpl clienteController;

    @Mock
    private ClienteService clienteService;

    @Test
    public void getClienteById_WhenOk_ThenReturnCliente(){
        ClienteDto clienteDto = new ClienteDto();

        when(clienteService.getClientesById(ID_CLIENTE)).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> resultado = clienteController.getClienteById(ID_CLIENTE);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(clienteDto);

        verify(clienteService).getClientesById(ID_CLIENTE);
    }

}
package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.ClienteControllerImpl;
import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.service.specification.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_PERSONA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerImplTest {

    @InjectMocks
    private ClienteControllerImpl clienteController;

    @Mock
    private ClienteService clienteService;

    @Test
    public void getClientes_WhenOk_ThenReturnClientes(){
        List<ClienteDto> clienteDtoList = List.of(new ClienteDto());
        String fuzzySearch = null;

        when(clienteService.getClientes(fuzzySearch)).thenReturn(clienteDtoList);

        ResponseEntity<List<ClienteDto>> resultado = clienteController.getClientes(fuzzySearch);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(clienteDtoList);

        verify(clienteService).getClientes(fuzzySearch);
    }

    @Test
    public void getClienteById_WhenOk_ThenReturnCliente(){
        ClienteDto clienteDto = new ClienteDto();

        when(clienteService.getClientesById(ID_PERSONA)).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> resultado = clienteController.getClienteById(ID_PERSONA);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(clienteDto);

        verify(clienteService).getClientesById(ID_PERSONA);
    }

}
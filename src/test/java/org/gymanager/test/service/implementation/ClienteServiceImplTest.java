package org.gymanager.test.service.implementation;

import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.model.domain.clientes.Cliente;
import org.gymanager.repository.specification.ClienteRepository;
import org.gymanager.repository.filters.ClienteSpecification;
import org.gymanager.service.implementation.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.gymanager.test.constants.Constantes.ID_PERSONA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @Test
    public void getClientes_WhenOk_ThenReturnClientes(){
        String fuzzySearch = "";
        List<Cliente> clienteList = List.of(mock(Cliente.class));
        List<ClienteDto> clienteDtoList = List.of(mock(ClienteDto.class));

        when(clienteRepository.findAll(any(ClienteSpecification.class))).thenReturn(clienteList);
        when(clienteEntityToDtoConverter.convert(clienteList)).thenReturn(clienteDtoList);

        List<ClienteDto> resultado = clienteService.getClientes(fuzzySearch);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo(clienteDtoList);

        verify(clienteRepository).findAll(any(ClienteSpecification.class));
        verify(clienteEntityToDtoConverter).convert(clienteList);
    }

    @Test
    public void getClientesById_WhenOk_ThenReturnCliente(){
        Cliente cliente = mock(Cliente.class);
        ClienteDto clienteDto = mock(ClienteDto.class);

        when(clienteRepository.findById(ID_PERSONA)).thenReturn(Optional.of(cliente));
        when(clienteEntityToDtoConverter.convert(cliente)).thenReturn(clienteDto);

        ClienteDto resultado = clienteService.getClientesById(ID_PERSONA);

        assertThat(resultado).isEqualTo(clienteDto);

        verify(clienteRepository).findById(ID_PERSONA);
        verify(clienteEntityToDtoConverter).convert(cliente);
    }

    @Test
    public void getClientesById_WhenClienteNoExiste_ThenThrownNotFound(){
        when(clienteRepository.findById(ID_PERSONA)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getClientesById(ID_PERSONA))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Cliente no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(clienteRepository).findById(ID_PERSONA);
    }
}
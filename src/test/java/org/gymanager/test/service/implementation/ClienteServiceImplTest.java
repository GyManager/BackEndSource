package org.gymanager.test.service.implementation;

import org.gymanager.model.domain.Cliente;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.ClienteSpecification;
import org.gymanager.repository.specification.ClienteRepository;
import org.gymanager.service.implementation.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.gymanager.test.constants.Constantes.ID_CLIENTE;
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

    @Test
    public void getClientes_WhenOk_ThenReturnClientes(){
        String fuzzySearch = "";
        Integer page = 1;
        Integer size = 10;
        ClienteSortBy sortBy = ClienteSortBy.NONE;
        Sort.Direction direction = Sort.Direction.ASC;

        Cliente cliente = mock(Cliente.class);

        when(clienteRepository.findAll(any(ClienteSpecification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cliente)));

        GyManagerPage<Cliente> resultado = clienteService.getClientes(fuzzySearch, page, size, sortBy, direction);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent().contains(cliente)).isTrue();

        verify(clienteRepository).findAll(any(ClienteSpecification.class), any(Pageable.class));
    }

    @Test
    public void getClientesById_WhenOk_ThenReturnCliente(){
        Cliente cliente = mock(Cliente.class);

        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.getClientesById(ID_CLIENTE);

        assertThat(resultado).isEqualTo(cliente);

        verify(clienteRepository).findById(ID_CLIENTE);
    }

    @Test
    public void getClientesById_WhenClienteNoExiste_ThenThrownNotFound(){
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getClientesById(ID_CLIENTE))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Cliente no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(clienteRepository).findById(ID_CLIENTE);
    }
}
package org.gymanager.test.service.implementation;

import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.domain.Cliente;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.ClienteSpecification;
import org.gymanager.repository.specification.ClienteRepository;
import org.gymanager.service.implementation.ClienteServiceImpl;
import org.gymanager.service.specification.ObjetivoService;
import org.gymanager.service.specification.UsuarioService;
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

    @Mock
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ObjetivoService objetivoService;

    @Test
    public void getClientesById_WhenOk_ThenReturnCliente(){
        Cliente cliente = mock(Cliente.class);
        ClienteDto clienteDto = mock(ClienteDto.class);

        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
        when(clienteEntityToDtoConverter.convert(cliente)).thenReturn(clienteDto);

        ClienteDto resultado = clienteService.getClientesById(ID_CLIENTE);

        assertThat(resultado).isEqualTo(clienteDto);

        verify(clienteRepository).findById(ID_CLIENTE);
        verify(clienteEntityToDtoConverter).convert(cliente);
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
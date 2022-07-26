package org.gymanager.test.converter;

import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.domain.Cliente;
import org.gymanager.model.domain.Objetivo;
import org.gymanager.model.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.DIRECCION;
import static org.gymanager.test.constants.Constantes.FECHA_NACIMIENTO;
import static org.gymanager.test.constants.Constantes.ID_CLIENTE;
import static org.gymanager.test.constants.Constantes.OBJETIVO;
import static org.gymanager.test.constants.Constantes.OBSERVACIONES;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClienteEntityToDtoConverterTest {

    @InjectMocks
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @Mock
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnUsuarioDto(){
        Usuario usuario = mock(Usuario.class);
        UsuarioDto usuarioDto = mock(UsuarioDto.class);

        Objetivo objetivo = new Objetivo();
        objetivo.setObjetivo(OBJETIVO);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(ID_CLIENTE);
        cliente.setUsuario(usuario);
        cliente.setDireccion(DIRECCION);
        cliente.setFechaNacimiento(FECHA_NACIMIENTO);
        cliente.setObservaciones(OBSERVACIONES);
        cliente.setObjetivo(objetivo);

        when(usuarioEntityToDtoConverter.convert(usuario)).thenReturn(usuarioDto);

        ClienteDto resultado = clienteEntityToDtoConverter.convert(List.of(cliente)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdCliente()).isEqualTo(ID_CLIENTE);
        assertThat(resultado.getUsuario()).isEqualTo(usuarioDto);
        assertThat(resultado.getDireccion()).isEqualTo(DIRECCION);
        assertThat(resultado.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO);
        assertThat(resultado.getObservaciones()).isEqualTo(OBSERVACIONES);
        assertThat(resultado.getObjetivo()).isEqualTo(OBJETIVO);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Cliente> clienteList = null;
        List<ClienteDto> resultado = clienteEntityToDtoConverter.convert(clienteList);

        assertThat(resultado).isNull();
    }
}
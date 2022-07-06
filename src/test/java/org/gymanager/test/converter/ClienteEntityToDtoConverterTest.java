package org.gymanager.test.converter;

import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.model.domain.clientes.Cliente;
import org.gymanager.model.domain.clientes.TipoDocumento;
import org.gymanager.model.domain.usuarios.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.APELLIDO;
import static org.gymanager.test.constants.Constantes.CELULAR;
import static org.gymanager.test.constants.Constantes.DIRECCION;
import static org.gymanager.test.constants.Constantes.FECHA_NACIMIENTO;
import static org.gymanager.test.constants.Constantes.ID_PERSONA;
import static org.gymanager.test.constants.Constantes.ID_TIPO_DOCUMENTO;
import static org.gymanager.test.constants.Constantes.ID_USUARIO;
import static org.gymanager.test.constants.Constantes.MAIL;
import static org.gymanager.test.constants.Constantes.NOMBRE;
import static org.gymanager.test.constants.Constantes.NUMERO_DOCUMENTO;
import static org.gymanager.test.constants.Constantes.OBJETIVO;
import static org.gymanager.test.constants.Constantes.TIPO_DOCUMENTO;


@ExtendWith(MockitoExtension.class)
class ClienteEntityToDtoConverterTest {

    @InjectMocks
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnUsuarioDto(){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(ID_USUARIO);
        usuario.setMail(MAIL);

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setIdTipoDocumento(ID_TIPO_DOCUMENTO);
        tipoDocumento.setTipo(TIPO_DOCUMENTO);

        Cliente cliente = new Cliente();
        cliente.setIdPersona(ID_PERSONA);
        cliente.setUsuario(usuario);
        cliente.setNumeroDocumento(NUMERO_DOCUMENTO);
        cliente.setTipoDocumento(tipoDocumento);
        cliente.setNombre(NOMBRE);
        cliente.setApellido(APELLIDO);
        cliente.setCelular(CELULAR);
        cliente.setDireccion(DIRECCION);
        cliente.setFechaNacimiento(FECHA_NACIMIENTO);
        cliente.setObjetivo(OBJETIVO);

        ClienteDto resultado = clienteEntityToDtoConverter.convert(List.of(cliente)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdPersona()).isEqualTo(ID_PERSONA);
        assertThat(resultado.getIdUsuario()).isEqualTo(ID_USUARIO);
        assertThat(resultado.getMail()).isEqualTo(MAIL);
        assertThat(resultado.getNumeroDocumento()).isEqualTo(NUMERO_DOCUMENTO);
        assertThat(resultado.getIdTipoDocumento()).isEqualTo(ID_TIPO_DOCUMENTO);
        assertThat(resultado.getTipoDocumento()).isEqualTo(TIPO_DOCUMENTO);
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE);
        assertThat(resultado.getApellido()).isEqualTo(APELLIDO);
        assertThat(resultado.getDireccion()).isEqualTo(DIRECCION);
        assertThat(resultado.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO);
        assertThat(resultado.getCelular()).isEqualTo(CELULAR);
        assertThat(resultado.getObjetivo()).isEqualTo(OBJETIVO);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Cliente> clienteList = null;
        List<ClienteDto> resultado = clienteEntityToDtoConverter.convert(clienteList);

        assertThat(resultado).isNull();
    }
}
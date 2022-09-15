package org.gymanager.test.converter;

import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.domain.Sexo;
import org.gymanager.model.domain.TipoDocumento;
import org.gymanager.model.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.APELLIDO;
import static org.gymanager.test.constants.Constantes.CELULAR;
import static org.gymanager.test.constants.Constantes.ID_USUARIO;
import static org.gymanager.test.constants.Constantes.MAIL;
import static org.gymanager.test.constants.Constantes.NOMBRE;
import static org.gymanager.test.constants.Constantes.NUMERO_DOCUMENTO;
import static org.gymanager.test.constants.Constantes.PASS;
import static org.gymanager.test.constants.Constantes.SEXO;
import static org.gymanager.test.constants.Constantes.TIPO_DOCUMENTO;

@ExtendWith(MockitoExtension.class)
class UsuarioEntityToDtoConverterTest {

    @InjectMocks
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnUsuarioDto(){
        LocalDateTime now = now();

        Sexo sexo = new Sexo();
        sexo.setSexo(SEXO);

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setTipo(TIPO_DOCUMENTO);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(ID_USUARIO);
        usuario.setNumeroDocumento(NUMERO_DOCUMENTO);
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNombre(NOMBRE);
        usuario.setApellido(APELLIDO);
        usuario.setSexo(sexo);
        usuario.setPass(PASS);
        usuario.setFechaAlta(now);
        usuario.setFechaBaja(now);
        usuario.setMail(MAIL);
        usuario.setCelular(CELULAR);

        UsuarioDto resultado = usuarioEntityToDtoConverter.convert(List.of(usuario)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdUsuario()).isEqualTo(ID_USUARIO);
        assertThat(resultado.getNumeroDocumento()).isEqualTo(NUMERO_DOCUMENTO);
        assertThat(resultado.getTipoDocumento()).isEqualTo(TIPO_DOCUMENTO);
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE);
        assertThat(resultado.getApellido()).isEqualTo(APELLIDO);
        assertThat(resultado.getSexo()).isEqualTo(SEXO);
        assertThat(resultado.getFechaAlta()).isEqualTo(now);
        assertThat(resultado.getFechaBaja()).isEqualTo(now);
        assertThat(resultado.getMail()).isEqualTo(MAIL);
        assertThat(resultado.getCelular()).isEqualTo(CELULAR);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Usuario> usuarioList = null;
        List<UsuarioDto> resultado = usuarioEntityToDtoConverter.convert(usuarioList);

        assertThat(resultado).isNull();
    }
}
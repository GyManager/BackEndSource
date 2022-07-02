package org.gymanager.test.converter;

import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.domain.usuarios.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.MAIL;
import static org.gymanager.test.constants.Constantes.NOMBRE_USUARIO;
import static org.gymanager.test.constants.Constantes.PASS;
import static org.gymanager.test.constants.Constantes.USUARIO_ID;

@ExtendWith(MockitoExtension.class)
class UsuarioEntityToDtoConverterTest {

    @InjectMocks
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnUsuarioDto(){
        LocalDate now = now();

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(USUARIO_ID);
        usuario.setNombre(NOMBRE_USUARIO);
        usuario.setPass(PASS);
        usuario.setMail(MAIL);
        usuario.setFechaAlta(now);
        usuario.setFechaBaja(now);

        UsuarioDto resultado = usuarioEntityToDtoConverter.convert(List.of(usuario)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdUsuario()).isEqualTo(USUARIO_ID);
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE_USUARIO);
        assertThat(resultado.getMail()).isEqualTo(MAIL);
        assertThat(resultado.getFechaAlta()).isEqualTo(now);
        assertThat(resultado.getFechaBaja()).isEqualTo(now);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Usuario> usuarioList = null;
        List<UsuarioDto> resultado = usuarioEntityToDtoConverter.convert(usuarioList);

        assertThat(resultado).isNull();
    }
}
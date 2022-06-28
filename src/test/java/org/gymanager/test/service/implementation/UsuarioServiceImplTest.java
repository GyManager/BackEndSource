package org.gymanager.test.service.implementation;

import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;
import org.gymanager.model.domain.usuarios.Permiso;
import org.gymanager.model.domain.usuarios.Rol;
import org.gymanager.model.domain.usuarios.Usuario;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.implementation.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gymanager.test.constants.Constantes.MAIL;
import static org.gymanager.test.constants.Constantes.NOMBRE_USUARIO;
import static org.gymanager.test.constants.Constantes.PASS;
import static org.gymanager.test.constants.Constantes.PERMISO_DOS;
import static org.gymanager.test.constants.Constantes.PERMISO_UNO;
import static org.gymanager.test.constants.Constantes.USUARIO_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void loadUserByUsername_WhenOk_ThenReturnSpringSecurityUser(){
        Permiso permisoUno = new Permiso();
        permisoUno.setNombrePermiso(PERMISO_UNO);
        Permiso permisoDos = new Permiso();
        permisoDos.setNombrePermiso(PERMISO_DOS);

        Rol rolUno = new Rol();
        rolUno.setPermisos(List.of(permisoUno));
        Rol rolDos = new Rol();
        rolDos.setPermisos(List.of(permisoDos));

        Usuario usuario = new Usuario();
        usuario.setMail(MAIL);
        usuario.setPass(PASS);
        usuario.setRoles(List.of(rolUno, rolDos));

        when(usuarioRepository.findByMail(MAIL)).thenReturn(Optional.of(usuario));

        UserDetails resultado = usuarioService.loadUserByUsername(MAIL);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo(MAIL);
        assertThat(resultado.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .containsExactlyInAnyOrder(PERMISO_UNO, PERMISO_DOS);
        assertThat(resultado.getPassword()).isEqualTo(PASS);

        verify(usuarioRepository).findByMail(MAIL);
    }

    @Test
    public void loadUserByUsername_WhenMailVacio_ThenThrowException(){
        assertThatThrownBy(() -> usuarioService.loadUserByUsername(null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("El mail de login no debe ser vacio");
    }

    @Test
    public void loadUserByUsername_WhenUsuarioNoExiste_ThenThrowException(){
        when(usuarioRepository.findByMail(MAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.loadUserByUsername(MAIL))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Usuario con mail")
                .hasMessageContaining("no encontrado")
                .hasMessageContaining(MAIL);
    }

    @Test
    public void addUsuario_WhenOk_ThenCrearUsuario(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();
        usuarioDtoRegistro.setMail(MAIL);
        usuarioDtoRegistro.setNombre(NOMBRE_USUARIO);
        usuarioDtoRegistro.setPass(PASS);
        usuarioDtoRegistro.setConfirmacionPass(PASS);

        when(usuarioRepository.findByMail(MAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(PASS)).thenReturn(String.valueOf(PASS.hashCode()));

        usuarioService.addUsuario(usuarioDtoRegistro);

        verify(usuarioRepository).findByMail(MAIL);
        verify(passwordEncoder).encode(PASS);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    public void addUsuario_WhenUsuarioYaExiste_ThenBadRequest(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();
        usuarioDtoRegistro.setMail(MAIL);
        usuarioDtoRegistro.setNombre(NOMBRE_USUARIO);
        usuarioDtoRegistro.setPass(PASS);
        usuarioDtoRegistro.setConfirmacionPass(PASS);

        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepository.findByMail(MAIL)).thenReturn(Optional.of(usuarioExistente));

        assertThatThrownBy(() -> usuarioService.addUsuario(usuarioDtoRegistro))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Ya existe un usuario registrado con el mail")
                .hasMessageContaining(MAIL)
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);

        verify(usuarioRepository).findByMail(MAIL);
    }

    @Test
    public void addUsuario_WhenPassNoSonIguales_ThenBadRequest(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();
        usuarioDtoRegistro.setMail(MAIL);
        usuarioDtoRegistro.setNombre(NOMBRE_USUARIO);
        usuarioDtoRegistro.setPass(PASS);
        usuarioDtoRegistro.setConfirmacionPass(PASS.concat("DISTINTA"));

        when(usuarioRepository.findByMail(MAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.addUsuario(usuarioDtoRegistro))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("La contraseña y la confirmacion de la contraseña no coinciden")
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);

        verify(usuarioRepository).findByMail(MAIL);
    }

    @Test
    public void getUsuarios_WhenOk_ThenReturnUsuarios() {
        List<Usuario> usuarioList = List.of(mock(Usuario.class));
        List<UsuarioDto> usuarioDtoList = List.of(mock(UsuarioDto.class));

        when(usuarioRepository.findAll()).thenReturn(usuarioList);
        when(usuarioEntityToDtoConverter.convert(usuarioList)).thenReturn(usuarioDtoList);

        List<UsuarioDto> resultado = usuarioService.getUsuarios();

        assertThat(resultado).isEqualTo(usuarioDtoList);

        verify(usuarioRepository).findAll();
        verify(usuarioEntityToDtoConverter).convert(usuarioList);
    }

    @Test
    public void getUsuarioById_WhenOk_ThenReturnUsuario() {
        Usuario usuario = mock(Usuario.class);
        UsuarioDto usuarioDto = mock(UsuarioDto.class);

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(usuarioEntityToDtoConverter.convert(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.getUsuarioById(USUARIO_ID);

        assertThat(resultado).isEqualTo(usuarioDto);

        verify(usuarioRepository).findById(USUARIO_ID);
        verify(usuarioEntityToDtoConverter).convert(usuario);
    }

    @Test
    public void getUsuarioById_WhenUsuarioNoExiste_ThenNotFound() {
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.getUsuarioById(USUARIO_ID))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usuario no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(usuarioRepository).findById(USUARIO_ID);
    }

    @Test
    public void updateUsuarioById_WhenOk_ThenUpdateUsuario(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();
        usuarioDtoRegistro.setMail(MAIL);
        usuarioDtoRegistro.setNombre(NOMBRE_USUARIO);
        usuarioDtoRegistro.setPass(PASS);
        usuarioDtoRegistro.setConfirmacionPass(PASS);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(USUARIO_ID);

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(PASS)).thenReturn(String.valueOf(PASS.hashCode()));

        usuarioService.updateUsuarioById(USUARIO_ID, usuarioDtoRegistro);

        assertThat(usuario.getMail()).isEqualTo(MAIL);
        assertThat(usuario.getPass()).isEqualTo(String.valueOf(PASS.hashCode()));
        assertThat(usuario.getNombre()).isEqualTo(NOMBRE_USUARIO);

        verify(usuarioRepository).findById(USUARIO_ID);
        verify(passwordEncoder).encode(PASS);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void updateUsuarioById_WhenUsuarioInexistente_ThenNotFound(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.updateUsuarioById(USUARIO_ID, usuarioDtoRegistro))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usuario no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(usuarioRepository).findById(USUARIO_ID);
    }

    @Test
    public void updateUsuarioById_WhenPassYConfirmacionNoIguales_ThenBadRequest(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();
        usuarioDtoRegistro.setMail(MAIL);
        usuarioDtoRegistro.setNombre(NOMBRE_USUARIO);
        usuarioDtoRegistro.setPass(PASS);
        usuarioDtoRegistro.setConfirmacionPass(PASS.concat("DISTINTA"));

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(USUARIO_ID);

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> usuarioService.updateUsuarioById(USUARIO_ID, usuarioDtoRegistro))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("La contraseña y la confirmacion de la contraseña no coinciden")
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);

        verify(usuarioRepository).findById(USUARIO_ID);
    }

    @Test
    public void deleteUsuarioById_WhenOk_ThenBorrarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(USUARIO_ID);

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));

        usuarioService.deleteUsuarioById(USUARIO_ID);

        verify(usuarioRepository).findById(USUARIO_ID);
        verify(usuarioRepository).delete(usuario);
    }
}
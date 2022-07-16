package org.gymanager.test.service.implementation;

import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.domain.Permiso;
import org.gymanager.model.domain.Rol;
import org.gymanager.model.domain.Usuario;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.implementation.UsuarioServiceImpl;
import org.gymanager.service.specification.SexoService;
import org.gymanager.service.specification.TipoDocumentoService;
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
import static org.gymanager.test.constants.Constantes.ID_USUARIO;
import static org.gymanager.test.constants.Constantes.MAIL;
import static org.gymanager.test.constants.Constantes.PASS;
import static org.gymanager.test.constants.Constantes.PERMISO_DOS;
import static org.gymanager.test.constants.Constantes.PERMISO_UNO;
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
    private TipoDocumentoService tipoDocumentoService;

    @Mock
    private SexoService sexoService;

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

        when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuario));
        when(usuarioEntityToDtoConverter.convert(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.getUsuarioById(ID_USUARIO);

        assertThat(resultado).isEqualTo(usuarioDto);

        verify(usuarioRepository).findById(ID_USUARIO);
        verify(usuarioEntityToDtoConverter).convert(usuario);
    }

    @Test
    public void getUsuarioById_WhenUsuarioNoExiste_ThenNotFound() {
        when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.getUsuarioById(ID_USUARIO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usuario no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(usuarioRepository).findById(ID_USUARIO);
    }
}
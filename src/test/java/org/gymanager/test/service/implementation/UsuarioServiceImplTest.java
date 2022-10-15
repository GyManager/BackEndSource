package org.gymanager.test.service.implementation;

import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.converter.UsuarioEntityToDtoDetailsConverter;
import org.gymanager.converter.UsuarioEntityToInfoDtoConverter;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.domain.Permiso;
import org.gymanager.model.domain.Rol;
import org.gymanager.model.domain.Usuario;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.UsuarioSpecification;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.implementation.UsuarioServiceImpl;
import org.gymanager.service.specification.RolService;
import org.gymanager.service.specification.SexoService;
import org.gymanager.service.specification.TipoDocumentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private UsuarioEntityToDtoDetailsConverter usuarioEntityToDtoDetailsConverter;

    @Mock
    private UsuarioEntityToInfoDtoConverter usuarioEntityToInfoDtoConverter;

    @Mock
    private TipoDocumentoService tipoDocumentoService;

    @Mock
    private SexoService sexoService;

    @Mock
    private RolService rolService;

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

        when(usuarioRepository.findByMailIgnoreCase(MAIL)).thenReturn(Optional.of(usuario));

        UserDetails resultado = usuarioService.loadUserByUsername(MAIL);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo(MAIL);
        assertThat(resultado.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .containsExactlyInAnyOrder(PERMISO_UNO, PERMISO_DOS);
        assertThat(resultado.getPassword()).isEqualTo(PASS);

        verify(usuarioRepository).findByMailIgnoreCase(MAIL);
    }

    @Test
    public void loadUserByUsername_WhenMailVacio_ThenThrowException(){
        assertThatThrownBy(() -> usuarioService.loadUserByUsername(null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("El mail de login no debe ser vacio");
    }

    @Test
    public void loadUserByUsername_WhenUsuarioNoExiste_ThenThrowException(){
        when(usuarioRepository.findByMailIgnoreCase(MAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.loadUserByUsername(MAIL))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Usuario con mail")
                .hasMessageContaining("no encontrado")
                .hasMessageContaining(MAIL);
    }

    @Test
    public void getUsuarios_WhenOk_ThenReturnUsuarios() {
        var search = "";
        var page = 1;
        var size = 10;
        var sortBy = UsuarioSortBy.NONE;
        var direction = Sort.Direction.ASC;
        var usuario = mock(Usuario.class);
        var usuarioDto = mock(UsuarioDto.class);

        when(usuarioRepository.findAll(any(UsuarioSpecification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(usuario)));
        when(usuarioEntityToDtoConverter.convert(usuario)).thenReturn(usuarioDto);

        GyManagerPage<UsuarioDto> resultado = usuarioService.getUsuarios(search, page, size, sortBy, direction);

        assertThat(resultado.getContent().get(0)).isEqualTo(usuarioDto);

        verify(usuarioRepository).findAll(any(UsuarioSpecification.class), any(Pageable.class));
        verify(usuarioEntityToDtoConverter).convert(usuario);
    }

    @Test
    public void getUsuarioById_WhenOk_ThenReturnUsuario() {
        Usuario usuario = mock(Usuario.class);
        var usuarioDtoDetails = mock(UsuarioDtoDetails.class);

        when(usuarioRepository.findById(ID_USUARIO)).thenReturn(Optional.of(usuario));
        when(usuarioEntityToDtoDetailsConverter.convert(usuario)).thenReturn(usuarioDtoDetails);

        UsuarioDto resultado = usuarioService.getUsuarioById(ID_USUARIO);

        assertThat(resultado).isEqualTo(usuarioDtoDetails);

        verify(usuarioRepository).findById(ID_USUARIO);
        verify(usuarioEntityToDtoDetailsConverter).convert(usuario);
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